import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICountryList, CountryList } from 'app/shared/model/country-list.model';
import { CountryListService } from './country-list.service';

@Component({
  selector: 'jhi-country-list-update',
  templateUrl: './country-list-update.component.html',
})
export class CountryListUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    countryName: [],
    countryCode: [],
  });

  constructor(protected countryListService: CountryListService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ countryList }) => {
      this.updateForm(countryList);
    });
  }

  updateForm(countryList: ICountryList): void {
    this.editForm.patchValue({
      id: countryList.id,
      countryName: countryList.countryName,
      countryCode: countryList.countryCode,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const countryList = this.createFromForm();
    if (countryList.id !== undefined) {
      this.subscribeToSaveResponse(this.countryListService.update(countryList));
    } else {
      this.subscribeToSaveResponse(this.countryListService.create(countryList));
    }
  }

  private createFromForm(): ICountryList {
    return {
      ...new CountryList(),
      id: this.editForm.get(['id'])!.value,
      countryName: this.editForm.get(['countryName'])!.value,
      countryCode: this.editForm.get(['countryCode'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICountryList>>): void {
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
