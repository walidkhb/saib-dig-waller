import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { DestinationChargeAmountComponent } from './destination-charge-amount.component';
import { DestinationChargeAmountDetailComponent } from './destination-charge-amount-detail.component';
import { DestinationChargeAmountUpdateComponent } from './destination-charge-amount-update.component';
import { DestinationChargeAmountDeleteDialogComponent } from './destination-charge-amount-delete-dialog.component';
import { destinationChargeAmountRoute } from './destination-charge-amount.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(destinationChargeAmountRoute)],
  declarations: [
    DestinationChargeAmountComponent,
    DestinationChargeAmountDetailComponent,
    DestinationChargeAmountUpdateComponent,
    DestinationChargeAmountDeleteDialogComponent,
  ],
  entryComponents: [DestinationChargeAmountDeleteDialogComponent],
})
export class SaibDigitalWalletDestinationChargeAmountModule {}
