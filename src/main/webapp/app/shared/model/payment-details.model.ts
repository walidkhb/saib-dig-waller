export interface IPaymentDetails {
  id?: number;
  payoutCurrency?: string;
  paymentMode?: string;
  purposeOfTransfer?: string;
  payOutCountryCode?: string;
  paymentDetails?: string;
}

export class PaymentDetails implements IPaymentDetails {
  constructor(
    public id?: number,
    public payoutCurrency?: string,
    public paymentMode?: string,
    public purposeOfTransfer?: string,
    public payOutCountryCode?: string,
    public paymentDetails?: string
  ) {}
}
