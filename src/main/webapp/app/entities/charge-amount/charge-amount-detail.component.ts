import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChargeAmount } from 'app/shared/model/charge-amount.model';

@Component({
  selector: 'jhi-charge-amount-detail',
  templateUrl: './charge-amount-detail.component.html',
})
export class ChargeAmountDetailComponent implements OnInit {
  chargeAmount: IChargeAmount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chargeAmount }) => (this.chargeAmount = chargeAmount));
  }

  previousState(): void {
    window.history.back();
  }
}
