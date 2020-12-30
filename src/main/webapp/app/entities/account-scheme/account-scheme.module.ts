import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { AccountSchemeComponent } from './account-scheme.component';
import { AccountSchemeDetailComponent } from './account-scheme-detail.component';
import { AccountSchemeUpdateComponent } from './account-scheme-update.component';
import { AccountSchemeDeleteDialogComponent } from './account-scheme-delete-dialog.component';
import { accountSchemeRoute } from './account-scheme.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(accountSchemeRoute)],
  declarations: [AccountSchemeComponent, AccountSchemeDetailComponent, AccountSchemeUpdateComponent, AccountSchemeDeleteDialogComponent],
  entryComponents: [AccountSchemeDeleteDialogComponent],
})
export class SaibDigitalWalletAccountSchemeModule {}
