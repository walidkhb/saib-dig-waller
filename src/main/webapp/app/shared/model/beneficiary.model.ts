import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { IBeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';
import { IAccountScheme } from 'app/shared/model/account-scheme.model';

export interface IBeneficiary {
  id?: number;
  nickName?: string;
  firstName?: string;
  lastName?: string;
  middleName?: string;
  beneficiaryId?: string;
  beneficiaryType?: string;
  address?: string;
  nationality?: string;
  telephone?: string;
  dateOfBirth?: string;
  iDNumber?: string;
  iDType?: string;
  beneficiaryRelation?: string;
  beneficiaryCity?: string;
  beneficiaryPhoneCountryCode?: string;
  beneficiarySourceOfFund?: string;
  beneficiaryZipCode?: string;
  beneficiaryStatus?: string;
  beneficiaryCurrency?: string;
  beneficiaryDetails?: IPaymentDetails;
  beneficiaryBank?: IBeneficiaryBank;
  beneficiaryAccount?: IAccountScheme;
}

export class Beneficiary implements IBeneficiary {
  constructor(
    public id?: number,
    public nickName?: string,
    public firstName?: string,
    public lastName?: string,
    public middleName?: string,
    public beneficiaryId?: string,
    public beneficiaryType?: string,
    public address?: string,
    public nationality?: string,
    public telephone?: string,
    public dateOfBirth?: string,
    public iDNumber?: string,
    public iDType?: string,
    public beneficiaryRelation?: string,
    public beneficiaryCity?: string,
    public beneficiaryPhoneCountryCode?: string,
    public beneficiarySourceOfFund?: string,
    public beneficiaryZipCode?: string,
    public beneficiaryStatus?: string,
    public beneficiaryCurrency?: string,
    public beneficiaryDetails?: IPaymentDetails,
    public beneficiaryBank?: IBeneficiaryBank,
    public beneficiaryAccount?: IAccountScheme
  ) {}
}
