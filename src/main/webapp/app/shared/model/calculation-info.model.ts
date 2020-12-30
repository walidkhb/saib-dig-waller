export interface ICalculationInfo {
  id?: number;
  customerId?: string;
  beneficiaryId?: number;
  fromCurrency?: string;
  toCurrency?: string;
  transactionAmount?: number;
  transactionCurrency?: string;
}

export class CalculationInfo implements ICalculationInfo {
  constructor(
    public id?: number,
    public customerId?: string,
    public beneficiaryId?: number,
    public fromCurrency?: string,
    public toCurrency?: string,
    public transactionAmount?: number,
    public transactionCurrency?: string
  ) {}
}
