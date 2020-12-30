import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmount } from 'app/shared/model/amount.model';
import { AmountService } from './amount.service';

@Component({
  templateUrl: './amount-delete-dialog.component.html',
})
export class AmountDeleteDialogComponent {
  amount?: IAmount;

  constructor(protected amountService: AmountService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.amountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('amountListModification');
      this.activeModal.close();
    });
  }
}
