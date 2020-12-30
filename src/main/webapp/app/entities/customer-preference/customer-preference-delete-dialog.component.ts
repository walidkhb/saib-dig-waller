import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerPreference } from 'app/shared/model/customer-preference.model';
import { CustomerPreferenceService } from './customer-preference.service';

@Component({
  templateUrl: './customer-preference-delete-dialog.component.html',
})
export class CustomerPreferenceDeleteDialogComponent {
  customerPreference?: ICustomerPreference;

  constructor(
    protected customerPreferenceService: CustomerPreferenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerPreferenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customerPreferenceListModification');
      this.activeModal.close();
    });
  }
}
