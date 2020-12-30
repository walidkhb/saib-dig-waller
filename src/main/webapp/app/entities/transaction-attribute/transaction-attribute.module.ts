import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { TransactionAttributeComponent } from './transaction-attribute.component';
import { TransactionAttributeDetailComponent } from './transaction-attribute-detail.component';
import { TransactionAttributeUpdateComponent } from './transaction-attribute-update.component';
import { TransactionAttributeDeleteDialogComponent } from './transaction-attribute-delete-dialog.component';
import { transactionAttributeRoute } from './transaction-attribute.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(transactionAttributeRoute)],
  declarations: [
    TransactionAttributeComponent,
    TransactionAttributeDetailComponent,
    TransactionAttributeUpdateComponent,
    TransactionAttributeDeleteDialogComponent,
  ],
  entryComponents: [TransactionAttributeDeleteDialogComponent],
})
export class SaibDigitalWalletTransactionAttributeModule {}
