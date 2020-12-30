import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChargeAmount } from 'app/shared/model/charge-amount.model';
import { ChargeAmountService } from './charge-amount.service';
import { ChargeAmountDeleteDialogComponent } from './charge-amount-delete-dialog.component';

@Component({
  selector: 'jhi-charge-amount',
  templateUrl: './charge-amount.component.html',
})
export class ChargeAmountComponent implements OnInit, OnDestroy {
  chargeAmounts?: IChargeAmount[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected chargeAmountService: ChargeAmountService,
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
      this.chargeAmountService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IChargeAmount[]>) => (this.chargeAmounts = res.body || []));
      return;
    }

    this.chargeAmountService.query().subscribe((res: HttpResponse<IChargeAmount[]>) => (this.chargeAmounts = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInChargeAmounts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IChargeAmount): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInChargeAmounts(): void {
    this.eventSubscriber = this.eventManager.subscribe('chargeAmountListModification', () => this.loadAll());
  }

  delete(chargeAmount: IChargeAmount): void {
    const modalRef = this.modalService.open(ChargeAmountDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chargeAmount = chargeAmount;
  }
}
