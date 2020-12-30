import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChargeAmount } from 'app/shared/model/charge-amount.model';
import { ChargeAmountService } from './charge-amount.service';

@Component({
  templateUrl: './charge-amount-delete-dialog.component.html',
})
export class ChargeAmountDeleteDialogComponent {
  chargeAmount?: IChargeAmount;

  constructor(
    protected chargeAmountService: ChargeAmountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chargeAmountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('chargeAmountListModification');
      this.activeModal.close();
    });
  }
}
