import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAccountScheme, AccountScheme } from 'app/shared/model/account-scheme.model';
import { AccountSchemeService } from './account-scheme.service';
import { IWallet } from 'app/shared/model/wallet.model';
import { WalletService } from 'app/entities/wallet/wallet.service';

@Component({
  selector: 'jhi-account-scheme-update',
  templateUrl: './account-scheme-update.component.html',
})
export class AccountSchemeUpdateComponent implements OnInit {
  isSaving = false;
  wallets: IWallet[] = [];

  editForm = this.fb.group({
    id: [],
    schemeName: [],
    identification: [],
    wallet: [],
  });

  constructor(
    protected accountSchemeService: AccountSchemeService,
    protected walletService: WalletService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountScheme }) => {
      this.updateForm(accountScheme);

      this.walletService.query().subscribe((res: HttpResponse<IWallet[]>) => (this.wallets = res.body || []));
    });
  }

  updateForm(accountScheme: IAccountScheme): void {
    this.editForm.patchValue({
      id: accountScheme.id,
      schemeName: accountScheme.schemeName,
      identification: accountScheme.identification,
      wallet: accountScheme.wallet,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountScheme = this.createFromForm();
    if (accountScheme.id !== undefined) {
      this.subscribeToSaveResponse(this.accountSchemeService.update(accountScheme));
    } else {
      this.subscribeToSaveResponse(this.accountSchemeService.create(accountScheme));
    }
  }

  private createFromForm(): IAccountScheme {
    return {
      ...new AccountScheme(),
      id: this.editForm.get(['id'])!.value,
      schemeName: this.editForm.get(['schemeName'])!.value,
      identification: this.editForm.get(['identification'])!.value,
      wallet: this.editForm.get(['wallet'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountScheme>>): void {
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

  trackById(index: number, item: IWallet): any {
    return item.id;
  }
}
