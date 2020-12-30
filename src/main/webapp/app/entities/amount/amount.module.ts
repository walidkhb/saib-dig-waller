import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { AmountComponent } from './amount.component';
import { AmountDetailComponent } from './amount-detail.component';
import { AmountUpdateComponent } from './amount-update.component';
import { AmountDeleteDialogComponent } from './amount-delete-dialog.component';
import { amountRoute } from './amount.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(amountRoute)],
  declarations: [AmountComponent, AmountDetailComponent, AmountUpdateComponent, AmountDeleteDialogComponent],
  entryComponents: [AmountDeleteDialogComponent],
})
export class SaibDigitalWalletAmountModule {}
