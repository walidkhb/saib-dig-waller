import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.SaibDigitalWalletCustomerModule),
      },
      {
        path: 'customer-details',
        loadChildren: () => import('./customer-details/customer-details.module').then(m => m.SaibDigitalWalletCustomerDetailsModule),
      },
      {
        path: 'customer-preference',
        loadChildren: () =>
          import('./customer-preference/customer-preference.module').then(m => m.SaibDigitalWalletCustomerPreferenceModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.SaibDigitalWalletAddressModule),
      },
      {
        path: 'kyc-info',
        loadChildren: () => import('./kyc-info/kyc-info.module').then(m => m.SaibDigitalWalletKYCInfoModule),
      },
      {
        path: 'finger-details',
        loadChildren: () => import('./finger-details/finger-details.module').then(m => m.SaibDigitalWalletFingerDetailsModule),
      },
      {
        path: 'wallet',
        loadChildren: () => import('./wallet/wallet.module').then(m => m.SaibDigitalWalletWalletModule),
      },
      {
        path: 'account-scheme',
        loadChildren: () => import('./account-scheme/account-scheme.module').then(m => m.SaibDigitalWalletAccountSchemeModule),
      },
      {
        path: 'transfer',
        loadChildren: () => import('./transfer/transfer.module').then(m => m.SaibDigitalWalletTransferModule),
      },
      {
        path: 'beneficiary',
        loadChildren: () => import('./beneficiary/beneficiary.module').then(m => m.SaibDigitalWalletBeneficiaryModule),
      },
      {
        path: 'payment-details',
        loadChildren: () => import('./payment-details/payment-details.module').then(m => m.SaibDigitalWalletPaymentDetailsModule),
      },
      {
        path: 'beneficiary-bank',
        loadChildren: () => import('./beneficiary-bank/beneficiary-bank.module').then(m => m.SaibDigitalWalletBeneficiaryBankModule),
      },
      {
        path: 'debtor',
        loadChildren: () => import('./debtor/debtor.module').then(m => m.SaibDigitalWalletDebtorModule),
      },
      {
        path: 'creditor',
        loadChildren: () => import('./creditor/creditor.module').then(m => m.SaibDigitalWalletCreditorModule),
      },
      {
        path: 'amount',
        loadChildren: () => import('./amount/amount.module').then(m => m.SaibDigitalWalletAmountModule),
      },
      {
        path: 'transaction-info',
        loadChildren: () => import('./transaction-info/transaction-info.module').then(m => m.SaibDigitalWalletTransactionInfoModule),
      },
      {
        path: 'transaction-attribute',
        loadChildren: () =>
          import('./transaction-attribute/transaction-attribute.module').then(m => m.SaibDigitalWalletTransactionAttributeModule),
      },
      {
        path: 'transaction-details',
        loadChildren: () =>
          import('./transaction-details/transaction-details.module').then(m => m.SaibDigitalWalletTransactionDetailsModule),
      },
      {
        path: 'transaction-history',
        loadChildren: () =>
          import('./transaction-history/transaction-history.module').then(m => m.SaibDigitalWalletTransactionHistoryModule),
      },
      {
        path: 'calculation-info',
        loadChildren: () => import('./calculation-info/calculation-info.module').then(m => m.SaibDigitalWalletCalculationInfoModule),
      },
      {
        path: 'calculation-info-details',
        loadChildren: () =>
          import('./calculation-info-details/calculation-info-details.module').then(m => m.SaibDigitalWalletCalculationInfoDetailsModule),
      },
      {
        path: 'charge-amount',
        loadChildren: () => import('./charge-amount/charge-amount.module').then(m => m.SaibDigitalWalletChargeAmountModule),
      },
      {
        path: 'destination-charge-amount',
        loadChildren: () =>
          import('./destination-charge-amount/destination-charge-amount.module').then(
            m => m.SaibDigitalWalletDestinationChargeAmountModule
          ),
      },
      {
        path: 'currency-list',
        loadChildren: () => import('./currency-list/currency-list.module').then(m => m.SaibDigitalWalletCurrencyListModule),
      },
      {
        path: 'bank-list',
        loadChildren: () => import('./bank-list/bank-list.module').then(m => m.SaibDigitalWalletBankListModule),
      },
      {
        path: 'country-list',
        loadChildren: () => import('./country-list/country-list.module').then(m => m.SaibDigitalWalletCountryListModule),
      },
      {
        path: 'country-code-list',
        loadChildren: () => import('./country-code-list/country-code-list.module').then(m => m.SaibDigitalWalletCountryCodeListModule),
      },
      {
        path: 'version-list',
        loadChildren: () => import('./version-list/version-list.module').then(m => m.SaibDigitalWalletVersionListModule),
      },
      {
        path: 'branch-list',
        loadChildren: () => import('./branch-list/branch-list.module').then(m => m.SaibDigitalWalletBranchListModule),
      },
      {
        path: 'district-list',
        loadChildren: () => import('./district-list/district-list.module').then(m => m.SaibDigitalWalletDistrictListModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SaibDigitalWalletEntityModule {}
