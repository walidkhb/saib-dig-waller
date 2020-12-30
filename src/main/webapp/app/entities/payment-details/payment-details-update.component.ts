import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaymentDetails, PaymentDetails } from 'app/shared/model/payment-details.model';
import { PaymentDetailsService } from './payment-details.service';

@Component({
  selector: 'jhi-payment-details-update',
  templateUrl: './payment-details-update.component.html',
})
export class PaymentDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    payoutCurrency: [],
    paymentMode: [],
    purposeOfTransfer: [],
    payOutCountryCode: [],
    paymentDetails: [],
  });

  constructor(protected paymentDetailsService: PaymentDetailsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentDetails }) => {
      this.updateForm(paymentDetails);
    });
  }

  updateForm(paymentDetails: IPaymentDetails): void {
    this.editForm.patchValue({
      id: paymentDetails.id,
      payoutCurrency: paymentDetails.payoutCurrency,
      paymentMode: paymentDetails.paymentMode,
      purposeOfTransfer: paymentDetails.purposeOfTransfer,
      payOutCountryCode: paymentDetails.payOutCountryCode,
      paymentDetails: paymentDetails.paymentDetails,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentDetails = this.createFromForm();
    if (paymentDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentDetailsService.update(paymentDetails));
    } else {
      this.subscribeToSaveResponse(this.paymentDetailsService.create(paymentDetails));
    }
  }

  private createFromForm(): IPaymentDetails {
    return {
      ...new PaymentDetails(),
      id: this.editForm.get(['id'])!.value,
      payoutCurrency: this.editForm.get(['payoutCurrency'])!.value,
      paymentMode: this.editForm.get(['paymentMode'])!.value,
      purposeOfTransfer: this.editForm.get(['purposeOfTransfer'])!.value,
      payOutCountryCode: this.editForm.get(['payOutCountryCode'])!.value,
      paymentDetails: this.editForm.get(['paymentDetails'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentDetails>>): void {
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
