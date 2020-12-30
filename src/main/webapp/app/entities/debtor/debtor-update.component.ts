import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDebtor, Debtor } from 'app/shared/model/debtor.model';
import { DebtorService } from './debtor.service';
import { IAccountScheme } from 'app/shared/model/account-scheme.model';
import { AccountSchemeService } from 'app/entities/account-scheme/account-scheme.service';
import { IWallet } from 'app/shared/model/wallet.model';
import { WalletService } from 'app/entities/wallet/wallet.service';
import { IAmount } from 'app/shared/model/amount.model';
import { AmountService } from 'app/entities/amount/amount.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

type SelectableEntity = IAccountScheme | IWallet | IAmount | ICustomer;

@Component({
  selector: 'jhi-debtor-update',
  templateUrl: './debtor-update.component.html',
})
export class DebtorUpdateComponent implements OnInit {
  isSaving = false;
  debtoraccounts: IAccountScheme[] = [];
  debtwalletids: IWallet[] = [];
  debtamounts: IAmount[] = [];
  debtcustomers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    debtorAccount: [],
    debtWalletId: [],
    debtAmount: [],
    debtCustomer: [],
  });

  constructor(
    protected debtorService: DebtorService,
    protected accountSchemeService: AccountSchemeService,
    protected walletService: WalletService,
    protected amountService: AmountService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ debtor }) => {
      this.updateForm(debtor);

      this.accountSchemeService
        .query({ filter: 'debtor-is-null' })
        .pipe(
          map((res: HttpResponse<IAccountScheme[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAccountScheme[]) => {
          if (!debtor.debtorAccount || !debtor.debtorAccount.id) {
            this.debtoraccounts = resBody;
          } else {
            this.accountSchemeService
              .find(debtor.debtorAccount.id)
              .pipe(
                map((subRes: HttpResponse<IAccountScheme>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAccountScheme[]) => (this.debtoraccounts = concatRes));
          }
        });

      this.walletService
        .query({ filter: 'debtor-is-null' })
        .pipe(
          map((res: HttpResponse<IWallet[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IWallet[]) => {
          if (!debtor.debtWalletId || !debtor.debtWalletId.id) {
            this.debtwalletids = resBody;
          } else {
            this.walletService
              .find(debtor.debtWalletId.id)
              .pipe(
                map((subRes: HttpResponse<IWallet>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IWallet[]) => (this.debtwalletids = concatRes));
          }
        });

      this.amountService
        .query({ filter: 'debtor-is-null' })
        .pipe(
          map((res: HttpResponse<IAmount[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAmount[]) => {
          if (!debtor.debtAmount || !debtor.debtAmount.id) {
            this.debtamounts = resBody;
          } else {
            this.amountService
              .find(debtor.debtAmount.id)
              .pipe(
                map((subRes: HttpResponse<IAmount>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAmount[]) => (this.debtamounts = concatRes));
          }
        });

      this.customerService
        .query({ filter: 'debtor-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomer[]) => {
          if (!debtor.debtCustomer || !debtor.debtCustomer.id) {
            this.debtcustomers = resBody;
          } else {
            this.customerService
              .find(debtor.debtCustomer.id)
              .pipe(
                map((subRes: HttpResponse<ICustomer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomer[]) => (this.debtcustomers = concatRes));
          }
        });
    });
  }

  updateForm(debtor: IDebtor): void {
    this.editForm.patchValue({
      id: debtor.id,
      debtorAccount: debtor.debtorAccount,
      debtWalletId: debtor.debtWalletId,
      debtAmount: debtor.debtAmount,
      debtCustomer: debtor.debtCustomer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const debtor = this.createFromForm();
    if (debtor.id !== undefined) {
      this.subscribeToSaveResponse(this.debtorService.update(debtor));
    } else {
      this.subscribeToSaveResponse(this.debtorService.create(debtor));
    }
  }

  private createFromForm(): IDebtor {
    return {
      ...new Debtor(),
      id: this.editForm.get(['id'])!.value,
      debtorAccount: this.editForm.get(['debtorAccount'])!.value,
      debtWalletId: this.editForm.get(['debtWalletId'])!.value,
      debtAmount: this.editForm.get(['debtAmount'])!.value,
      debtCustomer: this.editForm.get(['debtCustomer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDebtor>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
