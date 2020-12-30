import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDebtor } from 'app/shared/model/debtor.model';
import { DebtorService } from './debtor.service';
import { DebtorDeleteDialogComponent } from './debtor-delete-dialog.component';

@Component({
  selector: 'jhi-debtor',
  templateUrl: './debtor.component.html',
})
export class DebtorComponent implements OnInit, OnDestroy {
  debtors?: IDebtor[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected debtorService: DebtorService,
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
      this.debtorService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IDebtor[]>) => (this.debtors = res.body || []));
      return;
    }

    this.debtorService.query().subscribe((res: HttpResponse<IDebtor[]>) => (this.debtors = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDebtors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDebtor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDebtors(): void {
    this.eventSubscriber = this.eventManager.subscribe('debtorListModification', () => this.loadAll());
  }

  delete(debtor: IDebtor): void {
    const modalRef = this.modalService.open(DebtorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.debtor = debtor;
  }
}
