import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDistrictList } from 'app/shared/model/district-list.model';
import { DistrictListService } from './district-list.service';
import { DistrictListDeleteDialogComponent } from './district-list-delete-dialog.component';

@Component({
  selector: 'jhi-district-list',
  templateUrl: './district-list.component.html',
})
export class DistrictListComponent implements OnInit, OnDestroy {
  districtLists?: IDistrictList[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected districtListService: DistrictListService,
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
      this.districtListService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IDistrictList[]>) => (this.districtLists = res.body || []));
      return;
    }

    this.districtListService.query().subscribe((res: HttpResponse<IDistrictList[]>) => (this.districtLists = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDistrictLists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDistrictList): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDistrictLists(): void {
    this.eventSubscriber = this.eventManager.subscribe('districtListListModification', () => this.loadAll());
  }

  delete(districtList: IDistrictList): void {
    const modalRef = this.modalService.open(DistrictListDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.districtList = districtList;
  }
}
