import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerPreference } from 'app/shared/model/customer-preference.model';

@Component({
  selector: 'jhi-customer-preference-detail',
  templateUrl: './customer-preference-detail.component.html',
})
export class CustomerPreferenceDetailComponent implements OnInit {
  customerPreference: ICustomerPreference | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerPreference }) => (this.customerPreference = customerPreference));
  }

  previousState(): void {
    window.history.back();
  }
}
