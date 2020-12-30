import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICountryList } from 'app/shared/model/country-list.model';
import { CountryListService } from './country-list.service';
import { CountryListDeleteDialogComponent } from './country-list-delete-dialog.component';

@Component({
  selector: 'jhi-country-list',
  templateUrl: './country-list.component.html',
})
export class CountryListComponent implements OnInit, OnDestroy {
  countryLists?: ICountryList[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected countryListService: CountryListService,
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
      this.countryListService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICountryList[]>) => (this.countryLists = res.body || []));
      return;
    }

    this.countryListService.query().subscribe((res: HttpResponse<ICountryList[]>) => (this.countryLists = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCountryLists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICountryList): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCountryLists(): void {
    this.eventSubscriber = this.eventManager.subscribe('countryListListModification', () => this.loadAll());
  }

  delete(countryList: ICountryList): void {
    const modalRef = this.modalService.open(CountryListDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.countryList = countryList;
  }
}
