export interface ICountryList {
  id?: number;
  countryName?: string;
  countryCode?: string;
}

export class CountryList implements ICountryList {
  constructor(public id?: number, public countryName?: string, public countryCode?: string) {}
}
