export interface ICurrencyList {
  id?: number;
  currencyName?: string;
  currencyCode?: string;
}

export class CurrencyList implements ICurrencyList {
  constructor(public id?: number, public currencyName?: string, public currencyCode?: string) {}
}
