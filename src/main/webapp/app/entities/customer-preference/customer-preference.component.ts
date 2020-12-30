import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerPreference } from 'app/shared/model/customer-preference.model';
import { CustomerPreferenceService } from './customer-preference.service';
import { CustomerPreferenceDeleteDialogComponent } from './customer-preference-delete-dialog.component';

@Component({
  selector: 'jhi-customer-preference',
  templateUrl: './customer-preference.component.html',
})
export class CustomerPreferenceComponent implements OnInit, OnDestroy {
  customerPreferences?: ICustomerPreference[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected customerPreferenceService: CustomerPreferenceService,
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
      this.customerPreferenceService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICustomerPreference[]>) => (this.customerPreferences = res.body || []));
      return;
    }

    this.customerPreferenceService
      .query()
      .subscribe((res: HttpResponse<ICustomerPreference[]>) => (this.customerPreferences = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCustomerPreferences();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICustomerPreference): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCustomerPreferences(): void {
    this.eventSubscriber = this.eventManager.subscribe('customerPreferenceListModification', () => this.loadAll());
  }

  delete(customerPreference: ICustomerPreference): void {
    const modalRef = this.modalService.open(CustomerPreferenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customerPreference = customerPreference;
  }
}
