import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { CountryCodeListComponent } from './country-code-list.component';
import { CountryCodeListDetailComponent } from './country-code-list-detail.component';
import { CountryCodeListUpdateComponent } from './country-code-list-update.component';
import { CountryCodeListDeleteDialogComponent } from './country-code-list-delete-dialog.component';
import { countryCodeListRoute } from './country-code-list.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(countryCodeListRoute)],
  declarations: [
    CountryCodeListComponent,
    CountryCodeListDetailComponent,
    CountryCodeListUpdateComponent,
    CountryCodeListDeleteDialogComponent,
  ],
  entryComponents: [CountryCodeListDeleteDialogComponent],
})
export class SaibDigitalWalletCountryCodeListModule {}
