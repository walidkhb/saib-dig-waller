import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { PaymentDetailsService } from './payment-details.service';
import { PaymentDetailsDeleteDialogComponent } from './payment-details-delete-dialog.component';

@Component({
  selector: 'jhi-payment-details',
  templateUrl: './payment-details.component.html',
})
export class PaymentDetailsComponent implements OnInit, OnDestroy {
  paymentDetails?: IPaymentDetails[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected paymentDetailsService: PaymentDetailsService,
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
      this.paymentDetailsService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IPaymentDetails[]>) => (this.paymentDetails = res.body || []));
      return;
    }

    this.paymentDetailsService.query().subscribe((res: HttpResponse<IPaymentDetails[]>) => (this.paymentDetails = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPaymentDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPaymentDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPaymentDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('paymentDetailsListModification', () => this.loadAll());
  }

  delete(paymentDetails: IPaymentDetails): void {
    const modalRef = this.modalService.open(PaymentDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentDetails = paymentDetails;
  }
}
