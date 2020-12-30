import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICountryCodeList } from 'app/shared/model/country-code-list.model';
import { CountryCodeListService } from './country-code-list.service';
import { CountryCodeListDeleteDialogComponent } from './country-code-list-delete-dialog.component';

@Component({
  selector: 'jhi-country-code-list',
  templateUrl: './country-code-list.component.html',
})
export class CountryCodeListComponent implements OnInit, OnDestroy {
  countryCodeLists?: ICountryCodeList[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected countryCodeListService: CountryCodeListService,
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
      this.countryCodeListService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICountryCodeList[]>) => (this.countryCodeLists = res.body || []));
      return;
    }

    this.countryCodeListService.query().subscribe((res: HttpResponse<ICountryCodeList[]>) => (this.countryCodeLists = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCountryCodeLists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICountryCodeList): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCountryCodeLists(): void {
    this.eventSubscriber = this.eventManager.subscribe('countryCodeListListModification', () => this.loadAll());
  }

  delete(countryCodeList: ICountryCodeList): void {
    const modalRef = this.modalService.open(CountryCodeListDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.countryCodeList = countryCodeList;
  }
}
