import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentDetails } from 'app/shared/model/payment-details.model';

@Component({
  selector: 'jhi-payment-details-detail',
  templateUrl: './payment-details-detail.component.html',
})
export class PaymentDetailsDetailComponent implements OnInit {
  paymentDetails: IPaymentDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentDetails }) => (this.paymentDetails = paymentDetails));
  }

  previousState(): void {
    window.history.back();
  }
}
