import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBranchList } from 'app/shared/model/branch-list.model';

@Component({
  selector: 'jhi-branch-list-detail',
  templateUrl: './branch-list-detail.component.html',
})
export class BranchListDetailComponent implements OnInit {
  branchList: IBranchList | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ branchList }) => (this.branchList = branchList));
  }

  previousState(): void {
    window.history.back();
  }
}
