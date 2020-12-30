import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVersionList } from 'app/shared/model/version-list.model';
import { VersionListService } from './version-list.service';

@Component({
  templateUrl: './version-list-delete-dialog.component.html',
})
export class VersionListDeleteDialogComponent {
  versionList?: IVersionList;

  constructor(
    protected versionListService: VersionListService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.versionListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('versionListListModification');
      this.activeModal.close();
    });
  }
}
