import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionDetails } from 'app/shared/model/transaction-details.model';
import { TransactionDetailsService } from './transaction-details.service';

@Component({
  templateUrl: './transaction-details-delete-dialog.component.html',
})
export class TransactionDetailsDeleteDialogComponent {
  transactionDetails?: ITransactionDetails;

  constructor(
    protected transactionDetailsService: TransactionDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transactionDetailsListModification');
      this.activeModal.close();
    });
  }
}
