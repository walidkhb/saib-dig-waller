export interface ITransactionHistory {
  id?: number;
  dateTime?: string;
  tRReferenceNo?: string;
  beneficiaryName?: string;
  payMode?: string;
  bankName?: string;
  payOutAmount?: string;
  payOutCurrency?: string;
  exchangeRate?: string;
  payInAmount?: string;
  payInCurrency?: string;
  commission?: string;
  status?: string;
  description?: string;
  purposeCode?: string;
  purposeOfTransfer?: string;
}

export class TransactionHistory implements ITransactionHistory {
  constructor(
    public id?: number,
    public dateTime?: string,
    public tRReferenceNo?: string,
    public beneficiaryName?: string,
    public payMode?: string,
    public bankName?: string,
    public payOutAmount?: string,
    public payOutCurrency?: string,
    public exchangeRate?: string,
    public payInAmount?: string,
    public payInCurrency?: string,
    public commission?: string,
    public status?: string,
    public description?: string,
    public purposeCode?: string,
    public purposeOfTransfer?: string
  ) {}
}
