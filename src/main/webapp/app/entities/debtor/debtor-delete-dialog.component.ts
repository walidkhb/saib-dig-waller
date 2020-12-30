import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDebtor } from 'app/shared/model/debtor.model';
import { DebtorService } from './debtor.service';

@Component({
  templateUrl: './debtor-delete-dialog.component.html',
})
export class DebtorDeleteDialogComponent {
  debtor?: IDebtor;

  constructor(protected debtorService: DebtorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.debtorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('debtorListModification');
      this.activeModal.close();
    });
  }
}
