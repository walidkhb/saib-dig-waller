import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { FingerDetailsComponent } from './finger-details.component';
import { FingerDetailsDetailComponent } from './finger-details-detail.component';
import { FingerDetailsUpdateComponent } from './finger-details-update.component';
import { FingerDetailsDeleteDialogComponent } from './finger-details-delete-dialog.component';
import { fingerDetailsRoute } from './finger-details.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(fingerDetailsRoute)],
  declarations: [FingerDetailsComponent, FingerDetailsDetailComponent, FingerDetailsUpdateComponent, FingerDetailsDeleteDialogComponent],
  entryComponents: [FingerDetailsDeleteDialogComponent],
})
export class SaibDigitalWalletFingerDetailsModule {}
