import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICalculationInfo } from 'app/shared/model/calculation-info.model';
import { CalculationInfoService } from './calculation-info.service';

@Component({
  templateUrl: './calculation-info-delete-dialog.component.html',
})
export class CalculationInfoDeleteDialogComponent {
  calculationInfo?: ICalculationInfo;

  constructor(
    protected calculationInfoService: CalculationInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.calculationInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('calculationInfoListModification');
      this.activeModal.close();
    });
  }
}
