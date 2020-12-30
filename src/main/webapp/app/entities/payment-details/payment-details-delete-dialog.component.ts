import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { PaymentDetailsService } from './payment-details.service';

@Component({
  templateUrl: './payment-details-delete-dialog.component.html',
})
export class PaymentDetailsDeleteDialogComponent {
  paymentDetails?: IPaymentDetails;

  constructor(
    protected paymentDetailsService: PaymentDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentDetailsListModification');
      this.activeModal.close();
    });
  }
}
