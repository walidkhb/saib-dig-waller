import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICreditor } from 'app/shared/model/creditor.model';
import { CreditorService } from './creditor.service';
import { CreditorDeleteDialogComponent } from './creditor-delete-dialog.component';

@Component({
  selector: 'jhi-creditor',
  templateUrl: './creditor.component.html',
})
export class CreditorComponent implements OnInit, OnDestroy {
  creditors?: ICreditor[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected creditorService: CreditorService,
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
      this.creditorService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICreditor[]>) => (this.creditors = res.body || []));
      return;
    }

    this.creditorService.query().subscribe((res: HttpResponse<ICreditor[]>) => (this.creditors = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCreditors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICreditor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCreditors(): void {
    this.eventSubscriber = this.eventManager.subscribe('creditorListModification', () => this.loadAll());
  }

  delete(creditor: ICreditor): void {
    const modalRef = this.modalService.open(CreditorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.creditor = creditor;
  }
}
