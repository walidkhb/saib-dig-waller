import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { CalculationInfoComponent } from './calculation-info.component';
import { CalculationInfoDetailComponent } from './calculation-info-detail.component';
import { CalculationInfoUpdateComponent } from './calculation-info-update.component';
import { CalculationInfoDeleteDialogComponent } from './calculation-info-delete-dialog.component';
import { calculationInfoRoute } from './calculation-info.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(calculationInfoRoute)],
  declarations: [
    CalculationInfoComponent,
    CalculationInfoDetailComponent,
    CalculationInfoUpdateComponent,
    CalculationInfoDeleteDialogComponent,
  ],
  entryComponents: [CalculationInfoDeleteDialogComponent],
})
export class SaibDigitalWalletCalculationInfoModule {}
