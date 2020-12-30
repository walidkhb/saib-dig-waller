import { ITransactionAttribute } from 'app/shared/model/transaction-attribute.model';

export interface ITransactionInfo {
  id?: number;
  transactionType?: string;
  transactionId?: string;
  creditDebitIndicator?: string;
  creationDateTime?: string;
  status?: string;
  transactionAttr?: ITransactionAttribute;
}

export class TransactionInfo implements ITransactionInfo {
  constructor(
    public id?: number,
    public transactionType?: string,
    public transactionId?: string,
    public creditDebitIndicator?: string,
    public creationDateTime?: string,
    public status?: string,
    public transactionAttr?: ITransactionAttribute
  ) {}
}
