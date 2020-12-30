import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { CountryListComponent } from './country-list.component';
import { CountryListDetailComponent } from './country-list-detail.component';
import { CountryListUpdateComponent } from './country-list-update.component';
import { CountryListDeleteDialogComponent } from './country-list-delete-dialog.component';
import { countryListRoute } from './country-list.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(countryListRoute)],
  declarations: [CountryListComponent, CountryListDetailComponent, CountryListUpdateComponent, CountryListDeleteDialogComponent],
  entryComponents: [CountryListDeleteDialogComponent],
})
export class SaibDigitalWalletCountryListModule {}
