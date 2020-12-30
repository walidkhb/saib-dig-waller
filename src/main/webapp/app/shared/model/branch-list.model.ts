export interface IBranchList {
  id?: number;
  branchId?: string;
  branchName?: string;
}

export class BranchList implements IBranchList {
  constructor(public id?: number, public branchId?: string, public branchName?: string) {}
}
