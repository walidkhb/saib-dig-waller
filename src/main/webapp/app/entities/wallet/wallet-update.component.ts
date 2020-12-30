import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWallet, Wallet } from 'app/shared/model/wallet.model';
import { WalletService } from './wallet.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-wallet-update',
  templateUrl: './wallet-update.component.html',
})
export class WalletUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    walletId: [],
    status: [],
    statusUpdateDateTime: [],
    currency: [],
    accountType: [],
    accountSubType: [],
    description: [],
    customer: [],
  });

  constructor(
    protected walletService: WalletService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wallet }) => {
      this.updateForm(wallet);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(wallet: IWallet): void {
    this.editForm.patchValue({
      id: wallet.id,
      walletId: wallet.walletId,
      status: wallet.status,
      statusUpdateDateTime: wallet.statusUpdateDateTime,
      currency: wallet.currency,
      accountType: wallet.accountType,
      accountSubType: wallet.accountSubType,
      description: wallet.description,
      customer: wallet.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wallet = this.createFromForm();
    if (wallet.id !== undefined) {
      this.subscribeToSaveResponse(this.walletService.update(wallet));
    } else {
      this.subscribeToSaveResponse(this.walletService.create(wallet));
    }
  }

  private createFromForm(): IWallet {
    return {
      ...new Wallet(),
      id: this.editForm.get(['id'])!.value,
      walletId: this.editForm.get(['walletId'])!.value,
      status: this.editForm.get(['status'])!.value,
      statusUpdateDateTime: this.editForm.get(['statusUpdateDateTime'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      accountType: this.editForm.get(['accountType'])!.value,
      accountSubType: this.editForm.get(['accountSubType'])!.value,
      description: this.editForm.get(['description'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWallet>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
