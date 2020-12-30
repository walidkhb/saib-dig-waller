import { IBeneficiary } from 'app/shared/model/beneficiary.model';
import { IWallet } from 'app/shared/model/wallet.model';
import { IAmount } from 'app/shared/model/amount.model';

export interface ICreditor {
  id?: number;
  beneficiary?: IBeneficiary;
  credWalletId?: IWallet;
  credAmount?: IAmount;
}

export class Creditor implements ICreditor {
  constructor(public id?: number, public beneficiary?: IBeneficiary, public credWalletId?: IWallet, public credAmount?: IAmount) {}
}
