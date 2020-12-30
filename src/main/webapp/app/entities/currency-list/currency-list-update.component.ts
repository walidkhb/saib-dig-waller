import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICurrencyList, CurrencyList } from 'app/shared/model/currency-list.model';
import { CurrencyListService } from './currency-list.service';

@Component({
  selector: 'jhi-currency-list-update',
  templateUrl: './currency-list-update.component.html',
})
export class CurrencyListUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    currencyName: [],
    currencyCode: [],
  });

  constructor(protected currencyListService: CurrencyListService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currencyList }) => {
      this.updateForm(currencyList);
    });
  }

  updateForm(currencyList: ICurrencyList): void {
    this.editForm.patchValue({
      id: currencyList.id,
      currencyName: currencyList.currencyName,
      currencyCode: currencyList.currencyCode,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const currencyList = this.createFromForm();
    if (currencyList.id !== undefined) {
      this.subscribeToSaveResponse(this.currencyListService.update(currencyList));
    } else {
      this.subscribeToSaveResponse(this.currencyListService.create(currencyList));
    }
  }

  private createFromForm(): ICurrencyList {
    return {
      ...new CurrencyList(),
      id: this.editForm.get(['id'])!.value,
      currencyName: this.editForm.get(['currencyName'])!.value,
      currencyCode: this.editForm.get(['currencyCode'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrencyList>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
