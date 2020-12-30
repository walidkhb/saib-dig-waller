import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';
import { BeneficiaryBankService } from './beneficiary-bank.service';
import { BeneficiaryBankDeleteDialogComponent } from './beneficiary-bank-delete-dialog.component';

@Component({
  selector: 'jhi-beneficiary-bank',
  templateUrl: './beneficiary-bank.component.html',
})
export class BeneficiaryBankComponent implements OnInit, OnDestroy {
  beneficiaryBanks?: IBeneficiaryBank[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected beneficiaryBankService: BeneficiaryBankService,
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
      this.beneficiaryBankService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IBeneficiaryBank[]>) => (this.beneficiaryBanks = res.body || []));
      return;
    }

    this.beneficiaryBankService.query().subscribe((res: HttpResponse<IBeneficiaryBank[]>) => (this.beneficiaryBanks = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBeneficiaryBanks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBeneficiaryBank): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBeneficiaryBanks(): void {
    this.eventSubscriber = this.eventManager.subscribe('beneficiaryBankListModification', () => this.loadAll());
  }

  delete(beneficiaryBank: IBeneficiaryBank): void {
    const modalRef = this.modalService.open(BeneficiaryBankDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.beneficiaryBank = beneficiaryBank;
  }
}
