import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountScheme } from 'app/shared/model/account-scheme.model';
import { AccountSchemeService } from './account-scheme.service';

@Component({
  templateUrl: './account-scheme-delete-dialog.component.html',
})
export class AccountSchemeDeleteDialogComponent {
  accountScheme?: IAccountScheme;

  constructor(
    protected accountSchemeService: AccountSchemeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountSchemeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('accountSchemeListModification');
      this.activeModal.close();
    });
  }
}
