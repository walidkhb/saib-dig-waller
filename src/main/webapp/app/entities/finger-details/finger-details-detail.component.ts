import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFingerDetails } from 'app/shared/model/finger-details.model';

@Component({
  selector: 'jhi-finger-details-detail',
  templateUrl: './finger-details-detail.component.html',
})
export class FingerDetailsDetailComponent implements OnInit {
  fingerDetails: IFingerDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fingerDetails }) => (this.fingerDetails = fingerDetails));
  }

  previousState(): void {
    window.history.back();
  }
}
