import { ITransactionInfo } from 'app/shared/model/transaction-info.model';
import { IChargeAmount } from 'app/shared/model/charge-amount.model';
import { IDestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';

export interface ICalculationInfoDetails {
  id?: number;
  totalDebitAmount?: string;
  destinationAmount?: string;
  destinationExchangeRate?: string;
  destinationCurrencyIndicator?: string;
  discountAmount?: string;
  transCalculation?: ITransactionInfo;
  chargeAmount?: IChargeAmount;
  destChargeAmount?: IDestinationChargeAmount;
}

export class CalculationInfoDetails implements ICalculationInfoDetails {
  constructor(
    public id?: number,
    public totalDebitAmount?: string,
    public destinationAmount?: string,
    public destinationExchangeRate?: string,
    public destinationCurrencyIndicator?: string,
    public discountAmount?: string,
    public transCalculation?: ITransactionInfo,
    public chargeAmount?: IChargeAmount,
    public destChargeAmount?: IDestinationChargeAmount
  ) {}
}
