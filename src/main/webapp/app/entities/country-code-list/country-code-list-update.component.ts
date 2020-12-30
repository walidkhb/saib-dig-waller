import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICountryCodeList, CountryCodeList } from 'app/shared/model/country-code-list.model';
import { CountryCodeListService } from './country-code-list.service';

@Component({
  selector: 'jhi-country-code-list-update',
  templateUrl: './country-code-list-update.component.html',
})
export class CountryCodeListUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    countryName: [],
    countryCode: [],
    countryISDCode: [],
  });

  constructor(
    protected countryCodeListService: CountryCodeListService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ countryCodeList }) => {
      this.updateForm(countryCodeList);
    });
  }

  updateForm(countryCodeList: ICountryCodeList): void {
    this.editForm.patchValue({
      id: countryCodeList.id,
      countryName: countryCodeList.countryName,
      countryCode: countryCodeList.countryCode,
      countryISDCode: countryCodeList.countryISDCode,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const countryCodeList = this.createFromForm();
    if (countryCodeList.id !== undefined) {
      this.subscribeToSaveResponse(this.countryCodeListService.update(countryCodeList));
    } else {
      this.subscribeToSaveResponse(this.countryCodeListService.create(countryCodeList));
    }
  }

  private createFromForm(): ICountryCodeList {
    return {
      ...new CountryCodeList(),
      id: this.editForm.get(['id'])!.value,
      countryName: this.editForm.get(['countryName'])!.value,
      countryCode: this.editForm.get(['countryCode'])!.value,
      countryISDCode: this.editForm.get(['countryISDCode'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICountryCodeList>>): void {
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
