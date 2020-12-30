import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionInfo } from 'app/shared/model/transaction-info.model';

@Component({
  selector: 'jhi-transaction-info-detail',
  templateUrl: './transaction-info-detail.component.html',
})
export class TransactionInfoDetailComponent implements OnInit {
  transactionInfo: ITransactionInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionInfo }) => (this.transactionInfo = transactionInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
