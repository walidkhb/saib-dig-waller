export interface ICustomerPreference {
  id?: number;
  email?: string;
  language?: string;
}

export class CustomerPreference implements ICustomerPreference {
  constructor(public id?: number, public email?: string, public language?: string) {}
}
