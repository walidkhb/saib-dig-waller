import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { BankListComponent } from './bank-list.component';
import { BankListDetailComponent } from './bank-list-detail.component';
import { BankListUpdateComponent } from './bank-list-update.component';
import { BankListDeleteDialogComponent } from './bank-list-delete-dialog.component';
import { bankListRoute } from './bank-list.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(bankListRoute)],
  declarations: [BankListComponent, BankListDetailComponent, BankListUpdateComponent, BankListDeleteDialogComponent],
  entryComponents: [BankListDeleteDialogComponent],
})
export class SaibDigitalWalletBankListModule {}
