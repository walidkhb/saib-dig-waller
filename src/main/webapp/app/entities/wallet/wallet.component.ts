import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWallet } from 'app/shared/model/wallet.model';
import { WalletService } from './wallet.service';
import { WalletDeleteDialogComponent } from './wallet-delete-dialog.component';

@Component({
  selector: 'jhi-wallet',
  templateUrl: './wallet.component.html',
})
export class WalletComponent implements OnInit, OnDestroy {
  wallets?: IWallet[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected walletService: WalletService,
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
      this.walletService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IWallet[]>) => (this.wallets = res.body || []));
      return;
    }

    this.walletService.query().subscribe((res: HttpResponse<IWallet[]>) => (this.wallets = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWallets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWallet): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWallets(): void {
    this.eventSubscriber = this.eventManager.subscribe('walletListModification', () => this.loadAll());
  }

  delete(wallet: IWallet): void {
    const modalRef = this.modalService.open(WalletDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.wallet = wallet;
  }
}
