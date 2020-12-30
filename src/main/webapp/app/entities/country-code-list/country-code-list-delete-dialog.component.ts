import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICountryCodeList } from 'app/shared/model/country-code-list.model';
import { CountryCodeListService } from './country-code-list.service';

@Component({
  templateUrl: './country-code-list-delete-dialog.component.html',
})
export class CountryCodeListDeleteDialogComponent {
  countryCodeList?: ICountryCodeList;

  constructor(
    protected countryCodeListService: CountryCodeListService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.countryCodeListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('countryCodeListListModification');
      this.activeModal.close();
    });
  }
}
