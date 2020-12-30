import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransactionAttribute } from 'app/shared/model/transaction-attribute.model';
import { TransactionAttributeService } from './transaction-attribute.service';
import { TransactionAttributeDeleteDialogComponent } from './transaction-attribute-delete-dialog.component';

@Component({
  selector: 'jhi-transaction-attribute',
  templateUrl: './transaction-attribute.component.html',
})
export class TransactionAttributeComponent implements OnInit, OnDestroy {
  transactionAttributes?: ITransactionAttribute[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected transactionAttributeService: TransactionAttributeService,
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
      this.transactionAttributeService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ITransactionAttribute[]>) => (this.transactionAttributes = res.body || []));
      return;
    }

    this.transactionAttributeService
      .query()
      .subscribe((res: HttpResponse<ITransactionAttribute[]>) => (this.transactionAttributes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransactionAttributes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransactionAttribute): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransactionAttributes(): void {
    this.eventSubscriber = this.eventManager.subscribe('transactionAttributeListModification', () => this.loadAll());
  }

  delete(transactionAttribute: ITransactionAttribute): void {
    const modalRef = this.modalService.open(TransactionAttributeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transactionAttribute = transactionAttribute;
  }
}
