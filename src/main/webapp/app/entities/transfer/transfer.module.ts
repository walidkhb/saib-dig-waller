import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { TransferComponent } from './transfer.component';
import { TransferDetailComponent } from './transfer-detail.component';
import { TransferUpdateComponent } from './transfer-update.component';
import { TransferDeleteDialogComponent } from './transfer-delete-dialog.component';
import { transferRoute } from './transfer.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(transferRoute)],
  declarations: [TransferComponent, TransferDetailComponent, TransferUpdateComponent, TransferDeleteDialogComponent],
  entryComponents: [TransferDeleteDialogComponent],
})
export class SaibDigitalWalletTransferModule {}
