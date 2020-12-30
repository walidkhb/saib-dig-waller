import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountScheme } from 'app/shared/model/account-scheme.model';

@Component({
  selector: 'jhi-account-scheme-detail',
  templateUrl: './account-scheme-detail.component.html',
})
export class AccountSchemeDetailComponent implements OnInit {
  accountScheme: IAccountScheme | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountScheme }) => (this.accountScheme = accountScheme));
  }

  previousState(): void {
    window.history.back();
  }
}
