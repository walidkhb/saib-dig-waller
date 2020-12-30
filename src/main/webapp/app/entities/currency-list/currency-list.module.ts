import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { CurrencyListComponent } from './currency-list.component';
import { CurrencyListDetailComponent } from './currency-list-detail.component';
import { CurrencyListUpdateComponent } from './currency-list-update.component';
import { CurrencyListDeleteDialogComponent } from './currency-list-delete-dialog.component';
import { currencyListRoute } from './currency-list.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(currencyListRoute)],
  declarations: [CurrencyListComponent, CurrencyListDetailComponent, CurrencyListUpdateComponent, CurrencyListDeleteDialogComponent],
  entryComponents: [CurrencyListDeleteDialogComponent],
})
export class SaibDigitalWalletCurrencyListModule {}
