import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransactionHistory } from 'app/shared/model/transaction-history.model';
import { TransactionHistoryService } from './transaction-history.service';
import { TransactionHistoryDeleteDialogComponent } from './transaction-history-delete-dialog.component';

@Component({
  selector: 'jhi-transaction-history',
  templateUrl: './transaction-history.component.html',
})
export class TransactionHistoryComponent implements OnInit, OnDestroy {
  transactionHistories?: ITransactionHistory[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected transactionHistoryService: TransactionHistoryService,
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
      this.transactionHistoryService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ITransactionHistory[]>) => (this.transactionHistories = res.body || []));
      return;
    }

    this.transactionHistoryService
      .query()
      .subscribe((res: HttpResponse<ITransactionHistory[]>) => (this.transactionHistories = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransactionHistories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransactionHistory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransactionHistories(): void {
    this.eventSubscriber = this.eventManager.subscribe('transactionHistoryListModification', () => this.loadAll());
  }

  delete(transactionHistory: ITransactionHistory): void {
    const modalRef = this.modalService.open(TransactionHistoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transactionHistory = transactionHistory;
  }
}
