import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKYCInfo } from 'app/shared/model/kyc-info.model';
import { KYCInfoService } from './kyc-info.service';

@Component({
  templateUrl: './kyc-info-delete-dialog.component.html',
})
export class KYCInfoDeleteDialogComponent {
  kYCInfo?: IKYCInfo;

  constructor(protected kYCInfoService: KYCInfoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kYCInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kYCInfoListModification');
      this.activeModal.close();
    });
  }
}
