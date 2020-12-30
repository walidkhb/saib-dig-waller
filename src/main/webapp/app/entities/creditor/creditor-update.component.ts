import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICreditor, Creditor } from 'app/shared/model/creditor.model';
import { CreditorService } from './creditor.service';
import { IBeneficiary } from 'app/shared/model/beneficiary.model';
import { BeneficiaryService } from 'app/entities/beneficiary/beneficiary.service';
import { IWallet } from 'app/shared/model/wallet.model';
import { WalletService } from 'app/entities/wallet/wallet.service';
import { IAmount } from 'app/shared/model/amount.model';
import { AmountService } from 'app/entities/amount/amount.service';

type SelectableEntity = IBeneficiary | IWallet | IAmount;

@Component({
  selector: 'jhi-creditor-update',
  templateUrl: './creditor-update.component.html',
})
export class CreditorUpdateComponent implements OnInit {
  isSaving = false;
  beneficiaries: IBeneficiary[] = [];
  credwalletids: IWallet[] = [];
  credamounts: IAmount[] = [];

  editForm = this.fb.group({
    id: [],
    beneficiary: [],
    credWalletId: [],
    credAmount: [],
  });

  constructor(
    protected creditorService: CreditorService,
    protected beneficiaryService: BeneficiaryService,
    protected walletService: WalletService,
    protected amountService: AmountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ creditor }) => {
      this.updateForm(creditor);

      this.beneficiaryService
        .query({ filter: 'creditor-is-null' })
        .pipe(
          map((res: HttpResponse<IBeneficiary[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBeneficiary[]) => {
          if (!creditor.beneficiary || !creditor.beneficiary.id) {
            this.beneficiaries = resBody;
          } else {
            this.beneficiaryService
              .find(creditor.beneficiary.id)
              .pipe(
                map((subRes: HttpResponse<IBeneficiary>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBeneficiary[]) => (this.beneficiaries = concatRes));
          }
        });

      this.walletService
        .query({ filter: 'creditor-is-null' })
        .pipe(
          map((res: HttpResponse<IWallet[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IWallet[]) => {
          if (!creditor.credWalletId || !creditor.credWalletId.id) {
            this.credwalletids = resBody;
          } else {
            this.walletService
              .find(creditor.credWalletId.id)
              .pipe(
                map((subRes: HttpResponse<IWallet>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IWallet[]) => (this.credwalletids = concatRes));
          }
        });

      this.amountService
        .query({ filter: 'creditor-is-null' })
        .pipe(
          map((res: HttpResponse<IAmount[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAmount[]) => {
          if (!creditor.credAmount || !creditor.credAmount.id) {
            this.credamounts = resBody;
          } else {
            this.amountService
              .find(creditor.credAmount.id)
              .pipe(
                map((subRes: HttpResponse<IAmount>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAmount[]) => (this.credamounts = concatRes));
          }
        });
    });
  }

  updateForm(creditor: ICreditor): void {
    this.editForm.patchValue({
      id: creditor.id,
      beneficiary: creditor.beneficiary,
      credWalletId: creditor.credWalletId,
      credAmount: creditor.credAmount,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const creditor = this.createFromForm();
    if (creditor.id !== undefined) {
      this.subscribeToSaveResponse(this.creditorService.update(creditor));
    } else {
      this.subscribeToSaveResponse(this.creditorService.create(creditor));
    }
  }

  private createFromForm(): ICreditor {
    return {
      ...new Creditor(),
      id: this.editForm.get(['id'])!.value,
      beneficiary: this.editForm.get(['beneficiary'])!.value,
      credWalletId: this.editForm.get(['credWalletId'])!.value,
      credAmount: this.editForm.get(['credAmount'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreditor>>): void {
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
