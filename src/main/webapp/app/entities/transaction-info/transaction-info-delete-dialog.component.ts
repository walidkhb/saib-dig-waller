import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionInfo } from 'app/shared/model/transaction-info.model';
import { TransactionInfoService } from './transaction-info.service';

@Component({
  templateUrl: './transaction-info-delete-dialog.component.html',
})
export class TransactionInfoDeleteDialogComponent {
  transactionInfo?: ITransactionInfo;

  constructor(
    protected transactionInfoService: TransactionInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transactionInfoListModification');
      this.activeModal.close();
    });
  }
}
