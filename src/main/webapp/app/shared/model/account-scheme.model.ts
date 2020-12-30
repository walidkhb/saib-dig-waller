import { IWallet } from 'app/shared/model/wallet.model';

export interface IAccountScheme {
  id?: number;
  schemeName?: string;
  identification?: string;
  wallet?: IWallet;
}

export class AccountScheme implements IAccountScheme {
  constructor(public id?: number, public schemeName?: string, public identification?: string, public wallet?: IWallet) {}
}
