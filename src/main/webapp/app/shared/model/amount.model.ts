import { IChargeAmount } from 'app/shared/model/charge-amount.model';

export interface IAmount {
  id?: number;
  amount?: number;
  netAmount?: number;
  currency?: string;
  purposeOfTransfer?: string;
  walletChargeAmount?: IChargeAmount;
}

export class Amount implements IAmount {
  constructor(
    public id?: number,
    public amount?: number,
    public netAmount?: number,
    public currency?: string,
    public purposeOfTransfer?: string,
    public walletChargeAmount?: IChargeAmount
  ) {}
}
