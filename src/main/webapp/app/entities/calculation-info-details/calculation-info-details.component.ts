import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICalculationInfoDetails } from 'app/shared/model/calculation-info-details.model';
import { CalculationInfoDetailsService } from './calculation-info-details.service';
import { CalculationInfoDetailsDeleteDialogComponent } from './calculation-info-details-delete-dialog.component';

@Component({
  selector: 'jhi-calculation-info-details',
  templateUrl: './calculation-info-details.component.html',
})
export class CalculationInfoDetailsComponent implements OnInit, OnDestroy {
  calculationInfoDetails?: ICalculationInfoDetails[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected calculationInfoDetailsService: CalculationInfoDetailsService,
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
      this.calculationInfoDetailsService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICalculationInfoDetails[]>) => (this.calculationInfoDetails = res.body || []));
      return;
    }

    this.calculationInfoDetailsService
      .query()
      .subscribe((res: HttpResponse<ICalculationInfoDetails[]>) => (this.calculationInfoDetails = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCalculationInfoDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICalculationInfoDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCalculationInfoDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('calculationInfoDetailsListModification', () => this.loadAll());
  }

  delete(calculationInfoDetails: ICalculationInfoDetails): void {
    const modalRef = this.modalService.open(CalculationInfoDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.calculationInfoDetails = calculationInfoDetails;
  }
}
