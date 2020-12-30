import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { ChargeAmountComponent } from './charge-amount.component';
import { ChargeAmountDetailComponent } from './charge-amount-detail.component';
import { ChargeAmountUpdateComponent } from './charge-amount-update.component';
import { ChargeAmountDeleteDialogComponent } from './charge-amount-delete-dialog.component';
import { chargeAmountRoute } from './charge-amount.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(chargeAmountRoute)],
  declarations: [ChargeAmountComponent, ChargeAmountDetailComponent, ChargeAmountUpdateComponent, ChargeAmountDeleteDialogComponent],
  entryComponents: [ChargeAmountDeleteDialogComponent],
})
export class SaibDigitalWalletChargeAmountModule {}
