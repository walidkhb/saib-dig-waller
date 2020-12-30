import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBankList, BankList } from 'app/shared/model/bank-list.model';
import { BankListService } from './bank-list.service';

@Component({
  selector: 'jhi-bank-list-update',
  templateUrl: './bank-list-update.component.html',
})
export class BankListUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    bankId: [],
    bankName: [],
    branchIndicator: [],
    flagLabel: [],
  });

  constructor(protected bankListService: BankListService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankList }) => {
      this.updateForm(bankList);
    });
  }

  updateForm(bankList: IBankList): void {
    this.editForm.patchValue({
      id: bankList.id,
      bankId: bankList.bankId,
      bankName: bankList.bankName,
      branchIndicator: bankList.branchIndicator,
      flagLabel: bankList.flagLabel,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bankList = this.createFromForm();
    if (bankList.id !== undefined) {
      this.subscribeToSaveResponse(this.bankListService.update(bankList));
    } else {
      this.subscribeToSaveResponse(this.bankListService.create(bankList));
    }
  }

  private createFromForm(): IBankList {
    return {
      ...new BankList(),
      id: this.editForm.get(['id'])!.value,
      bankId: this.editForm.get(['bankId'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      branchIndicator: this.editForm.get(['branchIndicator'])!.value,
      flagLabel: this.editForm.get(['flagLabel'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankList>>): void {
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
