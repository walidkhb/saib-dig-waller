import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICalculationInfoDetails } from 'app/shared/model/calculation-info-details.model';
import { CalculationInfoDetailsService } from './calculation-info-details.service';

@Component({
  templateUrl: './calculation-info-details-delete-dialog.component.html',
})
export class CalculationInfoDetailsDeleteDialogComponent {
  calculationInfoDetails?: ICalculationInfoDetails;

  constructor(
    protected calculationInfoDetailsService: CalculationInfoDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.calculationInfoDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('calculationInfoDetailsListModification');
      this.activeModal.close();
    });
  }
}
