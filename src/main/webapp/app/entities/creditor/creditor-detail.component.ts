import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICreditor } from 'app/shared/model/creditor.model';

@Component({
  selector: 'jhi-creditor-detail',
  templateUrl: './creditor-detail.component.html',
})
export class CreditorDetailComponent implements OnInit {
  creditor: ICreditor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ creditor }) => (this.creditor = creditor));
  }

  previousState(): void {
    window.history.back();
  }
}
