import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBranchList } from 'app/shared/model/branch-list.model';
import { BranchListService } from './branch-list.service';
import { BranchListDeleteDialogComponent } from './branch-list-delete-dialog.component';

@Component({
  selector: 'jhi-branch-list',
  templateUrl: './branch-list.component.html',
})
export class BranchListComponent implements OnInit, OnDestroy {
  branchLists?: IBranchList[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected branchListService: BranchListService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.branchListService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IBranchList[]>) => (this.branchLists = res.body || []));
      return;
    }

    this.branchListService.query().subscribe((res: HttpResponse<IBranchList[]>) => (this.branchLists = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBranchLists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBranchList): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBranchLists(): void {
    this.eventSubscriber = this.eventManager.subscribe('branchListListModification', () => this.loadAll());
  }

  delete(branchList: IBranchList): void {
    const modalRef = this.modalService.open(BranchListDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.branchList = branchList;
  }
}
