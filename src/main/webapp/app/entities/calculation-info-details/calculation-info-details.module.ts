import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { CalculationInfoDetailsComponent } from './calculation-info-details.component';
import { CalculationInfoDetailsDetailComponent } from './calculation-info-details-detail.component';
import { CalculationInfoDetailsUpdateComponent } from './calculation-info-details-update.component';
import { CalculationInfoDetailsDeleteDialogComponent } from './calculation-info-details-delete-dialog.component';
import { calculationInfoDetailsRoute } from './calculation-info-details.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(calculationInfoDetailsRoute)],
  declarations: [
    CalculationInfoDetailsComponent,
    CalculationInfoDetailsDetailComponent,
    CalculationInfoDetailsUpdateComponent,
    CalculationInfoDetailsDeleteDialogComponent,
  ],
  entryComponents: [CalculationInfoDetailsDeleteDialogComponent],
})
export class SaibDigitalWalletCalculationInfoDetailsModule {}
