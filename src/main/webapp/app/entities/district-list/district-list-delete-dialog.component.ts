import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDistrictList } from 'app/shared/model/district-list.model';
import { DistrictListService } from './district-list.service';

@Component({
  templateUrl: './district-list-delete-dialog.component.html',
})
export class DistrictListDeleteDialogComponent {
  districtList?: IDistrictList;

  constructor(
    protected districtListService: DistrictListService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.districtListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('districtListListModification');
      this.activeModal.close();
    });
  }
}
