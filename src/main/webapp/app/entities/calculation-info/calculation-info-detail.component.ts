import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalculationInfo } from 'app/shared/model/calculation-info.model';

@Component({
  selector: 'jhi-calculation-info-detail',
  templateUrl: './calculation-info-detail.component.html',
})
export class CalculationInfoDetailComponent implements OnInit {
  calculationInfo: ICalculationInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calculationInfo }) => (this.calculationInfo = calculationInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
