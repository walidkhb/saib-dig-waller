import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBankList } from 'app/shared/model/bank-list.model';
import { BankListService } from './bank-list.service';

@Component({
  templateUrl: './bank-list-delete-dialog.component.html',
})
export class BankListDeleteDialogComponent {
  bankList?: IBankList;

  constructor(protected bankListService: BankListService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bankListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bankListListModification');
      this.activeModal.close();
    });
  }
}
