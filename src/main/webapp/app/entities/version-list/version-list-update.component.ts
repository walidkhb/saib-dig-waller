import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVersionList, VersionList } from 'app/shared/model/version-list.model';
import { VersionListService } from './version-list.service';

@Component({
  selector: 'jhi-version-list-update',
  templateUrl: './version-list-update.component.html',
})
export class VersionListUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    aPIRecord: [],
    versionNumber: [],
  });

  constructor(protected versionListService: VersionListService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ versionList }) => {
      this.updateForm(versionList);
    });
  }

  updateForm(versionList: IVersionList): void {
    this.editForm.patchValue({
      id: versionList.id,
      aPIRecord: versionList.aPIRecord,
      versionNumber: versionList.versionNumber,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const versionList = this.createFromForm();
    if (versionList.id !== undefined) {
      this.subscribeToSaveResponse(this.versionListService.update(versionList));
    } else {
      this.subscribeToSaveResponse(this.versionListService.create(versionList));
    }
  }

  private createFromForm(): IVersionList {
    return {
      ...new VersionList(),
      id: this.editForm.get(['id'])!.value,
      aPIRecord: this.editForm.get(['aPIRecord'])!.value,
      versionNumber: this.editForm.get(['versionNumber'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVersionList>>): void {
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
