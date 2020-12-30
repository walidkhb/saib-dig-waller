import { ICustomerDetails } from 'app/shared/model/customer-details.model';
import { ICustomerPreference } from 'app/shared/model/customer-preference.model';
import { IAddress } from 'app/shared/model/address.model';
import { IKYCInfo } from 'app/shared/model/kyc-info.model';
import { IFingerDetails } from 'app/shared/model/finger-details.model';
import { IWallet } from 'app/shared/model/wallet.model';

export interface ICustomer {
  id?: number;
  firstNameArabic?: string;
  fatherNameArabic?: string;
  grandFatherNameArabic?: string;
  grandFatherNameEnglish?: string;
  placeOfBirth?: string;
  iDIssueDate?: string;
  iDExpiryDate?: string;
  maritalStatus?: string;
  customerId?: string;
  profileStatus?: string;
  customerDetails?: ICustomerDetails;
  customerPreference?: ICustomerPreference;
  address?: IAddress;
  kycInfo?: IKYCInfo;
  fingerDetails?: IFingerDetails[];
  wallets?: IWallet[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public firstNameArabic?: string,
    public fatherNameArabic?: string,
    public grandFatherNameArabic?: string,
    public grandFatherNameEnglish?: string,
    public placeOfBirth?: string,
    public iDIssueDate?: string,
    public iDExpiryDate?: string,
    public maritalStatus?: string,
    public customerId?: string,
    public profileStatus?: string,
    public customerDetails?: ICustomerDetails,
    public customerPreference?: ICustomerPreference,
    public address?: IAddress,
    public kycInfo?: IKYCInfo,
    public fingerDetails?: IFingerDetails[],
    public wallets?: IWallet[]
  ) {}
}
