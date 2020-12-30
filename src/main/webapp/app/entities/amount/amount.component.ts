import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAmount } from 'app/shared/model/amount.model';
import { AmountService } from './amount.service';
import { AmountDeleteDialogComponent } from './amount-delete-dialog.component';

@Component({
  selector: 'jhi-amount',
  templateUrl: './amount.component.html',
})
export class AmountComponent implements OnInit, OnDestroy {
  amounts?: IAmount[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected amountService: AmountService,
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
      this.amountService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAmount[]>) => (this.amounts = res.body || []));
      return;
    }

    this.amountService.query().subscribe((res: HttpResponse<IAmount[]>) => (this.amounts = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAmounts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAmount): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAmounts(): void {
    this.eventSubscriber = this.eventManager.subscribe('amountListModification', () => this.loadAll());
  }

  delete(amount: IAmount): void {
    const modalRef = this.modalService.open(AmountDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.amount = amount;
  }
}
