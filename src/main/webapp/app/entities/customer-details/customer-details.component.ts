import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerDetails } from 'app/shared/model/customer-details.model';
import { CustomerDetailsService } from './customer-details.service';
import { CustomerDetailsDeleteDialogComponent } from './customer-details-delete-dialog.component';

@Component({
  selector: 'jhi-customer-details',
  templateUrl: './customer-details.component.html',
})
export class CustomerDetailsComponent implements OnInit, OnDestroy {
  customerDetails?: ICustomerDetails[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected customerDetailsService: CustomerDetailsService,
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
      this.customerDetailsService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICustomerDetails[]>) => (this.customerDetails = res.body || []));
      return;
    }

    this.customerDetailsService.query().subscribe((res: HttpResponse<ICustomerDetails[]>) => (this.customerDetails = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCustomerDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICustomerDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCustomerDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('customerDetailsListModification', () => this.loadAll());
  }

  delete(customerDetails: ICustomerDetails): void {
    const modalRef = this.modalService.open(CustomerDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customerDetails = customerDetails;
  }
}
