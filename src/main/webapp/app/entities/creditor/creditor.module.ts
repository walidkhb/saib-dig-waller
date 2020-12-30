import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { CreditorComponent } from './creditor.component';
import { CreditorDetailComponent } from './creditor-detail.component';
import { CreditorUpdateComponent } from './creditor-update.component';
import { CreditorDeleteDialogComponent } from './creditor-delete-dialog.component';
import { creditorRoute } from './creditor.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(creditorRoute)],
  declarations: [CreditorComponent, CreditorDetailComponent, CreditorUpdateComponent, CreditorDeleteDialogComponent],
  entryComponents: [CreditorDeleteDialogComponent],
})
export class SaibDigitalWalletCreditorModule {}
