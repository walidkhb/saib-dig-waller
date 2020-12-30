import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { TransactionDetailsComponent } from './transaction-details.component';
import { TransactionDetailsDetailComponent } from './transaction-details-detail.component';
import { TransactionDetailsUpdateComponent } from './transaction-details-update.component';
import { TransactionDetailsDeleteDialogComponent } from './transaction-details-delete-dialog.component';
import { transactionDetailsRoute } from './transaction-details.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(transactionDetailsRoute)],
  declarations: [
    TransactionDetailsComponent,
    TransactionDetailsDetailComponent,
    TransactionDetailsUpdateComponent,
    TransactionDetailsDeleteDialogComponent,
  ],
  entryComponents: [TransactionDetailsDeleteDialogComponent],
})
export class SaibDigitalWalletTransactionDetailsModule {}
