export interface ICustomerDetails {
  id?: number;
  nationalIdentityNumber?: string;
  idType?: string;
  dateOfBirth?: string;
  mobilePhoneNumber?: string;
  agentVerificationNumber?: string;
}

export class CustomerDetails implements ICustomerDetails {
  constructor(
    public id?: number,
    public nationalIdentityNumber?: string,
    public idType?: string,
    public dateOfBirth?: string,
    public mobilePhoneNumber?: string,
    public agentVerificationNumber?: string
  ) {}
}
