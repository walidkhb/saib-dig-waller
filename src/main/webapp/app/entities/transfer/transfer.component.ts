import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransfer } from 'app/shared/model/transfer.model';
import { TransferService } from './transfer.service';
import { TransferDeleteDialogComponent } from './transfer-delete-dialog.component';

@Component({
  selector: 'jhi-transfer',
  templateUrl: './transfer.component.html',
})
export class TransferComponent implements OnInit, OnDestroy {
  transfers?: ITransfer[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected transferService: TransferService,
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
      this.transferService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ITransfer[]>) => (this.transfers = res.body || []));
      return;
    }

    this.transferService.query().subscribe((res: HttpResponse<ITransfer[]>) => (this.transfers = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransfers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransfer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransfers(): void {
    this.eventSubscriber = this.eventManager.subscribe('transferListModification', () => this.loadAll());
  }

  delete(transfer: ITransfer): void {
    const modalRef = this.modalService.open(TransferDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transfer = transfer;
  }
}
