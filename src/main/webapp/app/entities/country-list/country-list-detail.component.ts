import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICountryList } from 'app/shared/model/country-list.model';

@Component({
  selector: 'jhi-country-list-detail',
  templateUrl: './country-list-detail.component.html',
})
export class CountryListDetailComponent implements OnInit {
  countryList: ICountryList | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ countryList }) => (this.countryList = countryList));
  }

  previousState(): void {
    window.history.back();
  }
}
