import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVersionList } from 'app/shared/model/version-list.model';
import { VersionListService } from './version-list.service';
import { VersionListDeleteDialogComponent } from './version-list-delete-dialog.component';

@Component({
  selector: 'jhi-version-list',
  templateUrl: './version-list.component.html',
})
export class VersionListComponent implements OnInit, OnDestroy {
  versionLists?: IVersionList[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected versionListService: VersionListService,
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
      this.versionListService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IVersionList[]>) => (this.versionLists = res.body || []));
      return;
    }

    this.versionListService.query().subscribe((res: HttpResponse<IVersionList[]>) => (this.versionLists = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVersionLists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVersionList): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVersionLists(): void {
    this.eventSubscriber = this.eventManager.subscribe('versionListListModification', () => this.loadAll());
  }

  delete(versionList: IVersionList): void {
    const modalRef = this.modalService.open(VersionListDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.versionList = versionList;
  }
}
