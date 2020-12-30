import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';

@Component({
  selector: 'jhi-beneficiary-bank-detail',
  templateUrl: './beneficiary-bank-detail.component.html',
})
export class BeneficiaryBankDetailComponent implements OnInit {
  beneficiaryBank: IBeneficiaryBank | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beneficiaryBank }) => (this.beneficiaryBank = beneficiaryBank));
  }

  previousState(): void {
    window.history.back();
  }
}
