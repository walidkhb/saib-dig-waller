export interface IKYCInfo {
  id?: number;
  level?: string;
  status?: string;
}

export class KYCInfo implements IKYCInfo {
  constructor(public id?: number, public level?: string, public status?: string) {}
}
