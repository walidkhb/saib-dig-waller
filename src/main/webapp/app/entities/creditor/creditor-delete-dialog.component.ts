import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICreditor } from 'app/shared/model/creditor.model';
import { CreditorService } from './creditor.service';

@Component({
  templateUrl: './creditor-delete-dialog.component.html',
})
export class CreditorDeleteDialogComponent {
  creditor?: ICreditor;

  constructor(protected creditorService: CreditorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.creditorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('creditorListModification');
      this.activeModal.close();
    });
  }
}
