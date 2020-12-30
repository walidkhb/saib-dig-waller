import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { CustomerPreferenceComponent } from './customer-preference.component';
import { CustomerPreferenceDetailComponent } from './customer-preference-detail.component';
import { CustomerPreferenceUpdateComponent } from './customer-preference-update.component';
import { CustomerPreferenceDeleteDialogComponent } from './customer-preference-delete-dialog.component';
import { customerPreferenceRoute } from './customer-preference.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(customerPreferenceRoute)],
  declarations: [
    CustomerPreferenceComponent,
    CustomerPreferenceDetailComponent,
    CustomerPreferenceUpdateComponent,
    CustomerPreferenceDeleteDialogComponent,
  ],
  entryComponents: [CustomerPreferenceDeleteDialogComponent],
})
export class SaibDigitalWalletCustomerPreferenceModule {}
