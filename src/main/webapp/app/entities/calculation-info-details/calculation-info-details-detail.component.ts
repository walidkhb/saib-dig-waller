import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalculationInfoDetails } from 'app/shared/model/calculation-info-details.model';

@Component({
  selector: 'jhi-calculation-info-details-detail',
  templateUrl: './calculation-info-details-detail.component.html',
})
export class CalculationInfoDetailsDetailComponent implements OnInit {
  calculationInfoDetails: ICalculationInfoDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calculationInfoDetails }) => (this.calculationInfoDetails = calculationInfoDetails));
  }

  previousState(): void {
    window.history.back();
  }
}
