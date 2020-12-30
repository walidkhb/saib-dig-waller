import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { TransactionInfoComponent } from './transaction-info.component';
import { TransactionInfoDetailComponent } from './transaction-info-detail.component';
import { TransactionInfoUpdateComponent } from './transaction-info-update.component';
import { TransactionInfoDeleteDialogComponent } from './transaction-info-delete-dialog.component';
import { transactionInfoRoute } from './transaction-info.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(transactionInfoRoute)],
  declarations: [
    TransactionInfoComponent,
    TransactionInfoDetailComponent,
    TransactionInfoUpdateComponent,
    TransactionInfoDeleteDialogComponent,
  ],
  entryComponents: [TransactionInfoDeleteDialogComponent],
})
export class SaibDigitalWalletTransactionInfoModule {}
