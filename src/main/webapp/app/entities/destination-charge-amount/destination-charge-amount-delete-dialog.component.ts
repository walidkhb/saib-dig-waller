import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';
import { DestinationChargeAmountService } from './destination-charge-amount.service';

@Component({
  templateUrl: './destination-charge-amount-delete-dialog.component.html',
})
export class DestinationChargeAmountDeleteDialogComponent {
  destinationChargeAmount?: IDestinationChargeAmount;

  constructor(
    protected destinationChargeAmountService: DestinationChargeAmountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.destinationChargeAmountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('destinationChargeAmountListModification');
      this.activeModal.close();
    });
  }
}
