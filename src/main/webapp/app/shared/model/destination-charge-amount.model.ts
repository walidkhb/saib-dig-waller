export interface IDestinationChargeAmount {
  id?: number;
  vATEstimated?: string;
  amountEstimated?: string;
}

export class DestinationChargeAmount implements IDestinationChargeAmount {
  constructor(public id?: number, public vATEstimated?: string, public amountEstimated?: string) {}
}
