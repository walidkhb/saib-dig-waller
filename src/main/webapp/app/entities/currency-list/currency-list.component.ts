import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICurrencyList } from 'app/shared/model/currency-list.model';
import { CurrencyListService } from './currency-list.service';
import { CurrencyListDeleteDialogComponent } from './currency-list-delete-dialog.component';

@Component({
  selector: 'jhi-currency-list',
  templateUrl: './currency-list.component.html',
})
export class CurrencyListComponent implements OnInit, OnDestroy {
  currencyLists?: ICurrencyList[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected currencyListService: CurrencyListService,
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
      this.currencyListService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICurrencyList[]>) => (this.currencyLists = res.body || []));
      return;
    }

    this.currencyListService.query().subscribe((res: HttpResponse<ICurrencyList[]>) => (this.currencyLists = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCurrencyLists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICurrencyList): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCurrencyLists(): void {
    this.eventSubscriber = this.eventManager.subscribe('currencyListListModification', () => this.loadAll());
  }

  delete(currencyList: ICurrencyList): void {
    const modalRef = this.modalService.open(CurrencyListDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.currencyList = currencyList;
  }
}
