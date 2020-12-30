import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBankList } from 'app/shared/model/bank-list.model';
import { BankListService } from './bank-list.service';
import { BankListDeleteDialogComponent } from './bank-list-delete-dialog.component';

@Component({
  selector: 'jhi-bank-list',
  templateUrl: './bank-list.component.html',
})
export class BankListComponent implements OnInit, OnDestroy {
  bankLists?: IBankList[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected bankListService: BankListService,
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
      this.bankListService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IBankList[]>) => (this.bankLists = res.body || []));
      return;
    }

    this.bankListService.query().subscribe((res: HttpResponse<IBankList[]>) => (this.bankLists = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBankLists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBankList): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBankLists(): void {
    this.eventSubscriber = this.eventManager.subscribe('bankListListModification', () => this.loadAll());
  }

  delete(bankList: IBankList): void {
    const modalRef = this.modalService.open(BankListDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bankList = bankList;
  }
}
