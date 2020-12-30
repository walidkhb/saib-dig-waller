import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKYCInfo } from 'app/shared/model/kyc-info.model';

@Component({
  selector: 'jhi-kyc-info-detail',
  templateUrl: './kyc-info-detail.component.html',
})
export class KYCInfoDetailComponent implements OnInit {
  kYCInfo: IKYCInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kYCInfo }) => (this.kYCInfo = kYCInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
