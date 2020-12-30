import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDistrictList } from 'app/shared/model/district-list.model';

@Component({
  selector: 'jhi-district-list-detail',
  templateUrl: './district-list-detail.component.html',
})
export class DistrictListDetailComponent implements OnInit {
  districtList: IDistrictList | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ districtList }) => (this.districtList = districtList));
  }

  previousState(): void {
    window.history.back();
  }
}
