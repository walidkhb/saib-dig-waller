import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionAttribute } from 'app/shared/model/transaction-attribute.model';
import { TransactionAttributeService } from './transaction-attribute.service';

@Component({
  templateUrl: './transaction-attribute-delete-dialog.component.html',
})
export class TransactionAttributeDeleteDialogComponent {
  transactionAttribute?: ITransactionAttribute;

  constructor(
    protected transactionAttributeService: TransactionAttributeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionAttributeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transactionAttributeListModification');
      this.activeModal.close();
    });
  }
}
