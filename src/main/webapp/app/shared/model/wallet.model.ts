import { IAccountScheme } from 'app/shared/model/account-scheme.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IWallet {
  id?: number;
  walletId?: string;
  status?: string;
  statusUpdateDateTime?: string;
  currency?: string;
  accountType?: string;
  accountSubType?: string;
  description?: string;
  accountSchemes?: IAccountScheme[];
  customer?: ICustomer;
}

export class Wallet implements IWallet {
  constructor(
    public id?: number,
    public walletId?: string,
    public status?: string,
    public statusUpdateDateTime?: string,
    public currency?: string,
    public accountType?: string,
    public accountSubType?: string,
    public description?: string,
    public accountSchemes?: IAccountScheme[],
    public customer?: ICustomer
  ) {}
}
