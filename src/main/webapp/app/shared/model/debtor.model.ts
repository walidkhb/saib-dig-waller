import { IAccountScheme } from 'app/shared/model/account-scheme.model';
import { IWallet } from 'app/shared/model/wallet.model';
import { IAmount } from 'app/shared/model/amount.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IDebtor {
  id?: number;
  debtorAccount?: IAccountScheme;
  debtWalletId?: IWallet;
  debtAmount?: IAmount;
  debtCustomer?: ICustomer;
}

export class Debtor implements IDebtor {
  constructor(
    public id?: number,
    public debtorAccount?: IAccountScheme,
    public debtWalletId?: IWallet,
    public debtAmount?: IAmount,
    public debtCustomer?: ICustomer
  ) {}
}
