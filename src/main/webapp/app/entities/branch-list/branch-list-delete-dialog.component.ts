import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBranchList } from 'app/shared/model/branch-list.model';
import { BranchListService } from './branch-list.service';

@Component({
  templateUrl: './branch-list-delete-dialog.component.html',
})
export class BranchListDeleteDialogComponent {
  branchList?: IBranchList;

  constructor(
    protected branchListService: BranchListService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.branchListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('branchListListModification');
      this.activeModal.close();
    });
  }
}
