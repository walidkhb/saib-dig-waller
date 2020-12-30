export interface IBeneficiaryBank {
  id?: number;
  bankCode?: string;
  bankName?: string;
  bankCountry?: string;
  bankBranchCode?: string;
  branchNameAndAddress?: string;
}

export class BeneficiaryBank implements IBeneficiaryBank {
  constructor(
    public id?: number,
    public bankCode?: string,
    public bankName?: string,
    public bankCountry?: string,
    public bankBranchCode?: string,
    public branchNameAndAddress?: string
  ) {}
}
