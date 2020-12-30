import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { CustomerDetailsComponent } from './customer-details.component';
import { CustomerDetailsDetailComponent } from './customer-details-detail.component';
import { CustomerDetailsUpdateComponent } from './customer-details-update.component';
import { CustomerDetailsDeleteDialogComponent } from './customer-details-delete-dialog.component';
import { customerDetailsRoute } from './customer-details.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(customerDetailsRoute)],
  declarations: [
    CustomerDetailsComponent,
    CustomerDetailsDetailComponent,
    CustomerDetailsUpdateComponent,
    CustomerDetailsDeleteDialogComponent,
  ],
  entryComponents: [CustomerDetailsDeleteDialogComponent],
})
export class SaibDigitalWalletCustomerDetailsModule {}
