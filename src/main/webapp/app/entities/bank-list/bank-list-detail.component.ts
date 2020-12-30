import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankList } from 'app/shared/model/bank-list.model';

@Component({
  selector: 'jhi-bank-list-detail',
  templateUrl: './bank-list-detail.component.html',
})
export class BankListDetailComponent implements OnInit {
  bankList: IBankList | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankList }) => (this.bankList = bankList));
  }

  previousState(): void {
    window.history.back();
  }
}
