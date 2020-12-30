export interface IVersionList {
  id?: number;
  aPIRecord?: string;
  versionNumber?: string;
}

export class VersionList implements IVersionList {
  constructor(public id?: number, public aPIRecord?: string, public versionNumber?: string) {}
}
