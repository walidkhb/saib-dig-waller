import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';
import { DestinationChargeAmountService } from './destination-charge-amount.service';
import { DestinationChargeAmountDeleteDialogComponent } from './destination-charge-amount-delete-dialog.component';

@Component({
  selector: 'jhi-destination-charge-amount',
  templateUrl: './destination-charge-amount.component.html',
})
export class DestinationChargeAmountComponent implements OnInit, OnDestroy {
  destinationChargeAmounts?: IDestinationChargeAmount[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected destinationChargeAmountService: DestinationChargeAmountService,
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
      this.destinationChargeAmountService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IDestinationChargeAmount[]>) => (this.destinationChargeAmounts = res.body || []));
      return;
    }

    this.destinationChargeAmountService
      .query()
      .subscribe((res: HttpResponse<IDestinationChargeAmount[]>) => (this.destinationChargeAmounts = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDestinationChargeAmounts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDestinationChargeAmount): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDestinationChargeAmounts(): void {
    this.eventSubscriber = this.eventManager.subscribe('destinationChargeAmountListModification', () => this.loadAll());
  }

  delete(destinationChargeAmount: IDestinationChargeAmount): void {
    const modalRef = this.modalService.open(DestinationChargeAmountDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.destinationChargeAmount = destinationChargeAmount;
  }
}
