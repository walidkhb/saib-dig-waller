export interface ICountryCodeList {
  id?: number;
  countryName?: string;
  countryCode?: string;
  countryISDCode?: string;
}

export class CountryCodeList implements ICountryCodeList {
  constructor(public id?: number, public countryName?: string, public countryCode?: string, public countryISDCode?: string) {}
}
