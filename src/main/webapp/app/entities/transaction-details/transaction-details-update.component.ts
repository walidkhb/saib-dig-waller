import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransactionDetails, TransactionDetails } from 'app/shared/model/transaction-details.model';
import { TransactionDetailsService } from './transaction-details.service';

@Component({
  selector: 'jhi-transaction-details-update',
  templateUrl: './transaction-details-update.component.html',
})
export class TransactionDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    debitAmount: [],
    debitCurrency: [],
    creditAmount: [],
    creditCurrency: [],
    exchangeRate: [],
    fees: [],
    purposeOfTransfer: [],
    partnerReferenceNumber: [],
  });

  constructor(
    protected transactionDetailsService: TransactionDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionDetails }) => {
      this.updateForm(transactionDetails);
    });
  }

  updateForm(transactionDetails: ITransactionDetails): void {
    this.editForm.patchValue({
      id: transactionDetails.id,
      debitAmount: transactionDetails.debitAmount,
      debitCurrency: transactionDetails.debitCurrency,
      creditAmount: transactionDetails.creditAmount,
      creditCurrency: transactionDetails.creditCurrency,
      exchangeRate: transactionDetails.exchangeRate,
      fees: transactionDetails.fees,
      purposeOfTransfer: transactionDetails.purposeOfTransfer,
      partnerReferenceNumber: transactionDetails.partnerReferenceNumber,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionDetails = this.createFromForm();
    if (transactionDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionDetailsService.update(transactionDetails));
    } else {
      this.subscribeToSaveResponse(this.transactionDetailsService.create(transactionDetails));
    }
  }

  private createFromForm(): ITransactionDetails {
    return {
      ...new TransactionDetails(),
      id: this.editForm.get(['id'])!.value,
      debitAmount: this.editForm.get(['debitAmount'])!.value,
      debitCurrency: this.editForm.get(['debitCurrency'])!.value,
      creditAmount: this.editForm.get(['creditAmount'])!.value,
      creditCurrency: this.editForm.get(['creditCurrency'])!.value,
      exchangeRate: this.editForm.get(['exchangeRate'])!.value,
      fees: this.editForm.get(['fees'])!.value,
      purposeOfTransfer: this.editForm.get(['purposeOfTransfer'])!.value,
      partnerReferenceNumber: this.editForm.get(['partnerReferenceNumber'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionDetails>>): void {
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
