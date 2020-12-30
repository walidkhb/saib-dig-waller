import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';

import { MetricsComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild([metricsRoute])],
  declarations: [MetricsComponent],
})
export class MetricsModule {}
