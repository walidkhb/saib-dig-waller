import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { BranchListComponent } from './branch-list.component';
import { BranchListDetailComponent } from './branch-list-detail.component';
import { BranchListUpdateComponent } from './branch-list-update.component';
import { BranchListDeleteDialogComponent } from './branch-list-delete-dialog.component';
import { branchListRoute } from './branch-list.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(branchListRoute)],
  declarations: [BranchListComponent, BranchListDetailComponent, BranchListUpdateComponent, BranchListDeleteDialogComponent],
  entryComponents: [BranchListDeleteDialogComponent],
})
export class SaibDigitalWalletBranchListModule {}
