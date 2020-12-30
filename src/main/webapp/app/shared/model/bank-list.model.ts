export interface IBankList {
  id?: number;
  bankId?: string;
  bankName?: string;
  branchIndicator?: string;
  flagLabel?: string;
}

export class BankList implements IBankList {
  constructor(
    public id?: number,
    public bankId?: string,
    public bankName?: string,
    public branchIndicator?: string,
    public flagLabel?: string
  ) {}
}
