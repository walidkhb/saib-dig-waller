import { ICustomer } from 'app/shared/model/customer.model';

export interface IFingerDetails {
  id?: number;
  fingerPrint?: string;
  fingerIndex?: string;
  customer?: ICustomer;
}

export class FingerDetails implements IFingerDetails {
  constructor(public id?: number, public fingerPrint?: string, public fingerIndex?: string, public customer?: ICustomer) {}
}
