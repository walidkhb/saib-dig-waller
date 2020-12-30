import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICurrencyList } from 'app/shared/model/currency-list.model';
import { CurrencyListService } from './currency-list.service';

@Component({
  templateUrl: './currency-list-delete-dialog.component.html',
})
export class CurrencyListDeleteDialogComponent {
  currencyList?: ICurrencyList;

  constructor(
    protected currencyListService: CurrencyListService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.currencyListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('currencyListListModification');
      this.activeModal.close();
    });
  }
}
