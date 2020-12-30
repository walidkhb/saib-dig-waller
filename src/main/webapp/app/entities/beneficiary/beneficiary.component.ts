import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBeneficiary } from 'app/shared/model/beneficiary.model';
import { BeneficiaryService } from './beneficiary.service';
import { BeneficiaryDeleteDialogComponent } from './beneficiary-delete-dialog.component';

@Component({
  selector: 'jhi-beneficiary',
  templateUrl: './beneficiary.component.html',
})
export class BeneficiaryComponent implements OnInit, OnDestroy {
  beneficiaries?: IBeneficiary[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected beneficiaryService: BeneficiaryService,
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
      this.beneficiaryService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IBeneficiary[]>) => (this.beneficiaries = res.body || []));
      return;
    }

    this.beneficiaryService.query().subscribe((res: HttpResponse<IBeneficiary[]>) => (this.beneficiaries = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBeneficiaries();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBeneficiary): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBeneficiaries(): void {
    this.eventSubscriber = this.eventManager.subscribe('beneficiaryListModification', () => this.loadAll());
  }

  delete(beneficiary: IBeneficiary): void {
    const modalRef = this.modalService.open(BeneficiaryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.beneficiary = beneficiary;
  }
}
