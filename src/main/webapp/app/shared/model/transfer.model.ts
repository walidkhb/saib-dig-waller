import { IDebtor } from 'app/shared/model/debtor.model';
import { ICreditor } from 'app/shared/model/creditor.model';
import { IBeneficiary } from 'app/shared/model/beneficiary.model';
import { ITransactionInfo } from 'app/shared/model/transaction-info.model';

export interface ITransfer {
  id?: number;
  debtDetails?: IDebtor;
  credDetails?: ICreditor;
  beneficiaryInfo?: IBeneficiary;
  transactionInfo?: ITransactionInfo;
}

export class Transfer implements ITransfer {
  constructor(
    public id?: number,
    public debtDetails?: IDebtor,
    public credDetails?: ICreditor,
    public beneficiaryInfo?: IBeneficiary,
    public transactionInfo?: ITransactionInfo
  ) {}
}
