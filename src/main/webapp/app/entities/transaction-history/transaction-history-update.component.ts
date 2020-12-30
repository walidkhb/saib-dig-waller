import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransactionHistory, TransactionHistory } from 'app/shared/model/transaction-history.model';
import { TransactionHistoryService } from './transaction-history.service';

@Component({
  selector: 'jhi-transaction-history-update',
  templateUrl: './transaction-history-update.component.html',
})
export class TransactionHistoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    dateTime: [],
    tRReferenceNo: [],
    beneficiaryName: [],
    payMode: [],
    bankName: [],
    payOutAmount: [],
    payOutCurrency: [],
    exchangeRate: [],
    payInAmount: [],
    payInCurrency: [],
    commission: [],
    status: [],
    description: [],
    purposeCode: [],
    purposeOfTransfer: [],
  });

  constructor(
    protected transactionHistoryService: TransactionHistoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionHistory }) => {
      this.updateForm(transactionHistory);
    });
  }

  updateForm(transactionHistory: ITransactionHistory): void {
    this.editForm.patchValue({
      id: transactionHistory.id,
      dateTime: transactionHistory.dateTime,
      tRReferenceNo: transactionHistory.tRReferenceNo,
      beneficiaryName: transactionHistory.beneficiaryName,
      payMode: transactionHistory.payMode,
      bankName: transactionHistory.bankName,
      payOutAmount: transactionHistory.payOutAmount,
      payOutCurrency: transactionHistory.payOutCurrency,
      exchangeRate: transactionHistory.exchangeRate,
      payInAmount: transactionHistory.payInAmount,
      payInCurrency: transactionHistory.payInCurrency,
      commission: transactionHistory.commission,
      status: transactionHistory.status,
      description: transactionHistory.description,
      purposeCode: transactionHistory.purposeCode,
      purposeOfTransfer: transactionHistory.purposeOfTransfer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionHistory = this.createFromForm();
    if (transactionHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionHistoryService.update(transactionHistory));
    } else {
      this.subscribeToSaveResponse(this.transactionHistoryService.create(transactionHistory));
    }
  }

  private createFromForm(): ITransactionHistory {
    return {
      ...new TransactionHistory(),
      id: this.editForm.get(['id'])!.value,
      dateTime: this.editForm.get(['dateTime'])!.value,
      tRReferenceNo: this.editForm.get(['tRReferenceNo'])!.value,
      beneficiaryName: this.editForm.get(['beneficiaryName'])!.value,
      payMode: this.editForm.get(['payMode'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      payOutAmount: this.editForm.get(['payOutAmount'])!.value,
      payOutCurrency: this.editForm.get(['payOutCurrency'])!.value,
      exchangeRate: this.editForm.get(['exchangeRate'])!.value,
      payInAmount: this.editForm.get(['payInAmount'])!.value,
      payInCurrency: this.editForm.get(['payInCurrency'])!.value,
      commission: this.editForm.get(['commission'])!.value,
      status: this.editForm.get(['status'])!.value,
      description: this.editForm.get(['description'])!.value,
      purposeCode: this.editForm.get(['purposeCode'])!.value,
      purposeOfTransfer: this.editForm.get(['purposeOfTransfer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionHistory>>): void {
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
