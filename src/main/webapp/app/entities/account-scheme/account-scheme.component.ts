import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountScheme } from 'app/shared/model/account-scheme.model';
import { AccountSchemeService } from './account-scheme.service';
import { AccountSchemeDeleteDialogComponent } from './account-scheme-delete-dialog.component';

@Component({
  selector: 'jhi-account-scheme',
  templateUrl: './account-scheme.component.html',
})
export class AccountSchemeComponent implements OnInit, OnDestroy {
  accountSchemes?: IAccountScheme[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected accountSchemeService: AccountSchemeService,
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
      this.accountSchemeService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAccountScheme[]>) => (this.accountSchemes = res.body || []));
      return;
    }

    this.accountSchemeService.query().subscribe((res: HttpResponse<IAccountScheme[]>) => (this.accountSchemes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAccountSchemes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAccountScheme): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAccountSchemes(): void {
    this.eventSubscriber = this.eventManager.subscribe('accountSchemeListModification', () => this.loadAll());
  }

  delete(accountScheme: IAccountScheme): void {
    const modalRef = this.modalService.open(AccountSchemeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accountScheme = accountScheme;
  }
}
