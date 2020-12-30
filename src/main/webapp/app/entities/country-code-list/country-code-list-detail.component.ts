import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICountryCodeList } from 'app/shared/model/country-code-list.model';

@Component({
  selector: 'jhi-country-code-list-detail',
  templateUrl: './country-code-list-detail.component.html',
})
export class CountryCodeListDetailComponent implements OnInit {
  countryCodeList: ICountryCodeList | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ countryCodeList }) => (this.countryCodeList = countryCodeList));
  }

  previousState(): void {
    window.history.back();
  }
}
