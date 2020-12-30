import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';

import { AuditsComponent } from './audits.component';

import { auditsRoute } from './audits.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild([auditsRoute])],
  declarations: [AuditsComponent],
})
export class AuditsModule {}
