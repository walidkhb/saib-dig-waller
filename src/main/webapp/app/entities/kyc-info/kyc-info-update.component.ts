import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IKYCInfo, KYCInfo } from 'app/shared/model/kyc-info.model';
import { KYCInfoService } from './kyc-info.service';

@Component({
  selector: 'jhi-kyc-info-update',
  templateUrl: './kyc-info-update.component.html',
})
export class KYCInfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    level: [],
    status: [],
  });

  constructor(protected kYCInfoService: KYCInfoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kYCInfo }) => {
      this.updateForm(kYCInfo);
    });
  }

  updateForm(kYCInfo: IKYCInfo): void {
    this.editForm.patchValue({
      id: kYCInfo.id,
      level: kYCInfo.level,
      status: kYCInfo.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kYCInfo = this.createFromForm();
    if (kYCInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.kYCInfoService.update(kYCInfo));
    } else {
      this.subscribeToSaveResponse(this.kYCInfoService.create(kYCInfo));
    }
  }

  private createFromForm(): IKYCInfo {
    return {
      ...new KYCInfo(),
      id: this.editForm.get(['id'])!.value,
      level: this.editForm.get(['level'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKYCInfo>>): void {
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
