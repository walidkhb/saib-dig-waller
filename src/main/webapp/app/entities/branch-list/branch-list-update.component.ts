import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBranchList, BranchList } from 'app/shared/model/branch-list.model';
import { BranchListService } from './branch-list.service';

@Component({
  selector: 'jhi-branch-list-update',
  templateUrl: './branch-list-update.component.html',
})
export class BranchListUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    branchId: [],
    branchName: [],
  });

  constructor(protected branchListService: BranchListService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ branchList }) => {
      this.updateForm(branchList);
    });
  }

  updateForm(branchList: IBranchList): void {
    this.editForm.patchValue({
      id: branchList.id,
      branchId: branchList.branchId,
      branchName: branchList.branchName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const branchList = this.createFromForm();
    if (branchList.id !== undefined) {
      this.subscribeToSaveResponse(this.branchListService.update(branchList));
    } else {
      this.subscribeToSaveResponse(this.branchListService.create(branchList));
    }
  }

  private createFromForm(): IBranchList {
    return {
      ...new BranchList(),
      id: this.editForm.get(['id'])!.value,
      branchId: this.editForm.get(['branchId'])!.value,
      branchName: this.editForm.get(['branchName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBranchList>>): void {
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
