import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';

@Component({
  selector: 'jhi-destination-charge-amount-detail',
  templateUrl: './destination-charge-amount-detail.component.html',
})
export class DestinationChargeAmountDetailComponent implements OnInit {
  destinationChargeAmount: IDestinationChargeAmount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ destinationChargeAmount }) => (this.destinationChargeAmount = destinationChargeAmount));
  }

  previousState(): void {
    window.history.back();
  }
}
