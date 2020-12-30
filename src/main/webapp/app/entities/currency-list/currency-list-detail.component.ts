import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurrencyList } from 'app/shared/model/currency-list.model';

@Component({
  selector: 'jhi-currency-list-detail',
  templateUrl: './currency-list-detail.component.html',
})
export class CurrencyListDetailComponent implements OnInit {
  currencyList: ICurrencyList | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currencyList }) => (this.currencyList = currencyList));
  }

  previousState(): void {
    window.history.back();
  }
}
