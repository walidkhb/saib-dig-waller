import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionAttribute } from 'app/shared/model/transaction-attribute.model';

@Component({
  selector: 'jhi-transaction-attribute-detail',
  templateUrl: './transaction-attribute-detail.component.html',
})
export class TransactionAttributeDetailComponent implements OnInit {
  transactionAttribute: ITransactionAttribute | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionAttribute }) => (this.transactionAttribute = transactionAttribute));
  }

  previousState(): void {
    window.history.back();
  }
}
