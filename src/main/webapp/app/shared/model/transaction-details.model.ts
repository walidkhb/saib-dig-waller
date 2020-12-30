export interface ITransactionDetails {
  id?: number;
  debitAmount?: string;
  debitCurrency?: string;
  creditAmount?: string;
  creditCurrency?: string;
  exchangeRate?: string;
  fees?: string;
  purposeOfTransfer?: string;
  partnerReferenceNumber?: string;
}

export class TransactionDetails implements ITransactionDetails {
  constructor(
    public id?: number,
    public debitAmount?: string,
    public debitCurrency?: string,
    public creditAmount?: string,
    public creditCurrency?: string,
    public exchangeRate?: string,
    public fees?: string,
    public purposeOfTransfer?: string,
    public partnerReferenceNumber?: string
  ) {}
}
