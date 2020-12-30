import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';

import { LogsComponent } from './logs.component';

import { logsRoute } from './logs.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild([logsRoute])],
  declarations: [LogsComponent],
})
export class LogsModule {}
