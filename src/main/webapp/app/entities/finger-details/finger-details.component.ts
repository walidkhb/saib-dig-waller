import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFingerDetails } from 'app/shared/model/finger-details.model';
import { FingerDetailsService } from './finger-details.service';
import { FingerDetailsDeleteDialogComponent } from './finger-details-delete-dialog.component';

@Component({
  selector: 'jhi-finger-details',
  templateUrl: './finger-details.component.html',
})
export class FingerDetailsComponent implements OnInit, OnDestroy {
  fingerDetails?: IFingerDetails[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected fingerDetailsService: FingerDetailsService,
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
      this.fingerDetailsService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IFingerDetails[]>) => (this.fingerDetails = res.body || []));
      return;
    }

    this.fingerDetailsService.query().subscribe((res: HttpResponse<IFingerDetails[]>) => (this.fingerDetails = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFingerDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFingerDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFingerDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('fingerDetailsListModification', () => this.loadAll());
  }

  delete(fingerDetails: IFingerDetails): void {
    const modalRef = this.modalService.open(FingerDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fingerDetails = fingerDetails;
  }
}
