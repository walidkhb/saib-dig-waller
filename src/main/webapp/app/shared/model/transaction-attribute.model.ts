export interface ITransactionAttribute {
  id?: number;
  narativeLine1?: string;
  narativeLine2?: string;
  narativeLine3?: string;
  narativeLine4?: string;
  clientRefNumber?: string;
}

export class TransactionAttribute implements ITransactionAttribute {
  constructor(
    public id?: number,
    public narativeLine1?: string,
    public narativeLine2?: string,
    public narativeLine3?: string,
    public narativeLine4?: string,
    public clientRefNumber?: string
  ) {}
}
