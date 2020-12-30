import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { DistrictListComponent } from './district-list.component';
import { DistrictListDetailComponent } from './district-list-detail.component';
import { DistrictListUpdateComponent } from './district-list-update.component';
import { DistrictListDeleteDialogComponent } from './district-list-delete-dialog.component';
import { districtListRoute } from './district-list.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(districtListRoute)],
  declarations: [DistrictListComponent, DistrictListDetailComponent, DistrictListUpdateComponent, DistrictListDeleteDialogComponent],
  entryComponents: [DistrictListDeleteDialogComponent],
})
export class SaibDigitalWalletDistrictListModule {}
