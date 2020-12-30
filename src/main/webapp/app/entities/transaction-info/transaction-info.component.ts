import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransactionInfo } from 'app/shared/model/transaction-info.model';
import { TransactionInfoService } from './transaction-info.service';
import { TransactionInfoDeleteDialogComponent } from './transaction-info-delete-dialog.component';

@Component({
  selector: 'jhi-transaction-info',
  templateUrl: './transaction-info.component.html',
})
export class TransactionInfoComponent implements OnInit, OnDestroy {
  transactionInfos?: ITransactionInfo[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected transactionInfoService: TransactionInfoService,
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
      this.transactionInfoService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ITransactionInfo[]>) => (this.transactionInfos = res.body || []));
      return;
    }

    this.transactionInfoService.query().subscribe((res: HttpResponse<ITransactionInfo[]>) => (this.transactionInfos = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransactionInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransactionInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransactionInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('transactionInfoListModification', () => this.loadAll());
  }

  delete(transactionInfo: ITransactionInfo): void {
    const modalRef = this.modalService.open(TransactionInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transactionInfo = transactionInfo;
  }
}
