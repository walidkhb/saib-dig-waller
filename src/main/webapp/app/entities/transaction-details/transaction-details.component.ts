import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransactionDetails } from 'app/shared/model/transaction-details.model';
import { TransactionDetailsService } from './transaction-details.service';
import { TransactionDetailsDeleteDialogComponent } from './transaction-details-delete-dialog.component';

@Component({
  selector: 'jhi-transaction-details',
  templateUrl: './transaction-details.component.html',
})
export class TransactionDetailsComponent implements OnInit, OnDestroy {
  transactionDetails?: ITransactionDetails[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected transactionDetailsService: TransactionDetailsService,
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
      this.transactionDetailsService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ITransactionDetails[]>) => (this.transactionDetails = res.body || []));
      return;
    }

    this.transactionDetailsService
      .query()
      .subscribe((res: HttpResponse<ITransactionDetails[]>) => (this.transactionDetails = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransactionDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransactionDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransactionDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('transactionDetailsListModification', () => this.loadAll());
  }

  delete(transactionDetails: ITransactionDetails): void {
    const modalRef = this.modalService.open(TransactionDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transactionDetails = transactionDetails;
  }
}
