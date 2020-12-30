import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICountryList } from 'app/shared/model/country-list.model';
import { CountryListService } from './country-list.service';

@Component({
  templateUrl: './country-list-delete-dialog.component.html',
})
export class CountryListDeleteDialogComponent {
  countryList?: ICountryList;

  constructor(
    protected countryListService: CountryListService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.countryListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('countryListListModification');
      this.activeModal.close();
    });
  }
}
