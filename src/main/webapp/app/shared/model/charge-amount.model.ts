export interface IChargeAmount {
  id?: number;
  amount?: string;
  currency?: string;
  vAT?: string;
}

export class ChargeAmount implements IChargeAmount {
  constructor(public id?: number, public amount?: string, public currency?: string, public vAT?: string) {}
}
