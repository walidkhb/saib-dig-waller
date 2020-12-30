import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { DebtorComponent } from './debtor.component';
import { DebtorDetailComponent } from './debtor-detail.component';
import { DebtorUpdateComponent } from './debtor-update.component';
import { DebtorDeleteDialogComponent } from './debtor-delete-dialog.component';
import { debtorRoute } from './debtor.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(debtorRoute)],
  declarations: [DebtorComponent, DebtorDetailComponent, DebtorUpdateComponent, DebtorDeleteDialogComponent],
  entryComponents: [DebtorDeleteDialogComponent],
})
export class SaibDigitalWalletDebtorModule {}
