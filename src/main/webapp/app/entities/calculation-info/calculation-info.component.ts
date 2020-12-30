import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICalculationInfo } from 'app/shared/model/calculation-info.model';
import { CalculationInfoService } from './calculation-info.service';
import { CalculationInfoDeleteDialogComponent } from './calculation-info-delete-dialog.component';

@Component({
  selector: 'jhi-calculation-info',
  templateUrl: './calculation-info.component.html',
})
export class CalculationInfoComponent implements OnInit, OnDestroy {
  calculationInfos?: ICalculationInfo[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected calculationInfoService: CalculationInfoService,
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
      this.calculationInfoService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICalculationInfo[]>) => (this.calculationInfos = res.body || []));
      return;
    }

    this.calculationInfoService.query().subscribe((res: HttpResponse<ICalculationInfo[]>) => (this.calculationInfos = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCalculationInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICalculationInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCalculationInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('calculationInfoListModification', () => this.loadAll());
  }

  delete(calculationInfo: ICalculationInfo): void {
    const modalRef = this.modalService.open(CalculationInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.calculationInfo = calculationInfo;
  }
}
