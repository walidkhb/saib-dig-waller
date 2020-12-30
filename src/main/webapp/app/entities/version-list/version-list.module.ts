import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { VersionListComponent } from './version-list.component';
import { VersionListDetailComponent } from './version-list-detail.component';
import { VersionListUpdateComponent } from './version-list-update.component';
import { VersionListDeleteDialogComponent } from './version-list-delete-dialog.component';
import { versionListRoute } from './version-list.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(versionListRoute)],
  declarations: [VersionListComponent, VersionListDetailComponent, VersionListUpdateComponent, VersionListDeleteDialogComponent],
  entryComponents: [VersionListDeleteDialogComponent],
})
export class SaibDigitalWalletVersionListModule {}
