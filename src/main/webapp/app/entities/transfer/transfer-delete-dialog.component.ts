import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransfer } from 'app/shared/model/transfer.model';
import { TransferService } from './transfer.service';

@Component({
  templateUrl: './transfer-delete-dialog.component.html',
})
export class TransferDeleteDialogComponent {
  transfer?: ITransfer;

  constructor(protected transferService: TransferService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transferService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transferListModification');
      this.activeModal.close();
    });
  }
}
