import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { BeneficiaryBankComponent } from './beneficiary-bank.component';
import { BeneficiaryBankDetailComponent } from './beneficiary-bank-detail.component';
import { BeneficiaryBankUpdateComponent } from './beneficiary-bank-update.component';
import { BeneficiaryBankDeleteDialogComponent } from './beneficiary-bank-delete-dialog.component';
import { beneficiaryBankRoute } from './beneficiary-bank.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(beneficiaryBankRoute)],
  declarations: [
    BeneficiaryBankComponent,
    BeneficiaryBankDetailComponent,
    BeneficiaryBankUpdateComponent,
    BeneficiaryBankDeleteDialogComponent,
  ],
  entryComponents: [BeneficiaryBankDeleteDialogComponent],
})
export class SaibDigitalWalletBeneficiaryBankModule {}
