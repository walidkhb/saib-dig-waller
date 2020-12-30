import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';
import { BeneficiaryBankService } from './beneficiary-bank.service';

@Component({
  templateUrl: './beneficiary-bank-delete-dialog.component.html',
})
export class BeneficiaryBankDeleteDialogComponent {
  beneficiaryBank?: IBeneficiaryBank;

  constructor(
    protected beneficiaryBankService: BeneficiaryBankService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.beneficiaryBankService.delete(id).subscribe(() => {
      this.eventManager.broadcast('beneficiaryBankListModification');
      this.activeModal.close();
    });
  }
}
