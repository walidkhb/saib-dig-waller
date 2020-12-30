import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKYCInfo } from 'app/shared/model/kyc-info.model';
import { KYCInfoService } from './kyc-info.service';
import { KYCInfoDeleteDialogComponent } from './kyc-info-delete-dialog.component';

@Component({
  selector: 'jhi-kyc-info',
  templateUrl: './kyc-info.component.html',
})
export class KYCInfoComponent implements OnInit, OnDestroy {
  kYCInfos?: IKYCInfo[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected kYCInfoService: KYCInfoService,
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
      this.kYCInfoService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IKYCInfo[]>) => (this.kYCInfos = res.body || []));
      return;
    }

    this.kYCInfoService.query().subscribe((res: HttpResponse<IKYCInfo[]>) => (this.kYCInfos = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKYCInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKYCInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKYCInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('kYCInfoListModification', () => this.loadAll());
  }

  delete(kYCInfo: IKYCInfo): void {
    const modalRef = this.modalService.open(KYCInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.kYCInfo = kYCInfo;
  }
}
