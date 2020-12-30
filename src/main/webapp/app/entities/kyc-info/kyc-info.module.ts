import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { KYCInfoComponent } from './kyc-info.component';
import { KYCInfoDetailComponent } from './kyc-info-detail.component';
import { KYCInfoUpdateComponent } from './kyc-info-update.component';
import { KYCInfoDeleteDialogComponent } from './kyc-info-delete-dialog.component';
import { kYCInfoRoute } from './kyc-info.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(kYCInfoRoute)],
  declarations: [KYCInfoComponent, KYCInfoDetailComponent, KYCInfoUpdateComponent, KYCInfoDeleteDialogComponent],
  entryComponents: [KYCInfoDeleteDialogComponent],
})
export class SaibDigitalWalletKYCInfoModule {}
