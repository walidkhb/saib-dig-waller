import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDistrictList, DistrictList } from 'app/shared/model/district-list.model';
import { DistrictListService } from './district-list.service';

@Component({
  selector: 'jhi-district-list-update',
  templateUrl: './district-list-update.component.html',
})
export class DistrictListUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    districtId: [],
    districtName: [],
  });

  constructor(protected districtListService: DistrictListService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ districtList }) => {
      this.updateForm(districtList);
    });
  }

  updateForm(districtList: IDistrictList): void {
    this.editForm.patchValue({
      id: districtList.id,
      districtId: districtList.districtId,
      districtName: districtList.districtName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const districtList = this.createFromForm();
    if (districtList.id !== undefined) {
      this.subscribeToSaveResponse(this.districtListService.update(districtList));
    } else {
      this.subscribeToSaveResponse(this.districtListService.create(districtList));
    }
  }

  private createFromForm(): IDistrictList {
    return {
      ...new DistrictList(),
      id: this.editForm.get(['id'])!.value,
      districtId: this.editForm.get(['districtId'])!.value,
      districtName: this.editForm.get(['districtName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistrictList>>): void {
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
