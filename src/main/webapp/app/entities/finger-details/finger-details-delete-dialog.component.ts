import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFingerDetails } from 'app/shared/model/finger-details.model';
import { FingerDetailsService } from './finger-details.service';

@Component({
  templateUrl: './finger-details-delete-dialog.component.html',
})
export class FingerDetailsDeleteDialogComponent {
  fingerDetails?: IFingerDetails;

  constructor(
    protected fingerDetailsService: FingerDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fingerDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fingerDetailsListModification');
      this.activeModal.close();
    });
  }
}
