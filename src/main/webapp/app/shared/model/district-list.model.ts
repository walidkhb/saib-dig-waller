export interface IDistrictList {
  id?: number;
  districtId?: string;
  districtName?: string;
}

export class DistrictList implements IDistrictList {
  constructor(public id?: number, public districtId?: string, public districtName?: string) {}
}
