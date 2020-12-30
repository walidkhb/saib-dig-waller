import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAmount, Amount } from 'app/shared/model/amount.model';
import { AmountService } from './amount.service';
import { IChargeAmount } from 'app/shared/model/charge-amount.model';
import { ChargeAmountService } from 'app/entities/charge-amount/charge-amount.service';

@Component({
  selector: 'jhi-amount-update',
  templateUrl: './amount-update.component.html',
})
export class AmountUpdateComponent implements OnInit {
  isSaving = false;
  walletchargeamounts: IChargeAmount[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    netAmount: [],
    currency: [],
    purposeOfTransfer: [],
    walletChargeAmount: [],
  });

  constructor(
    protected amountService: AmountService,
    protected chargeAmountService: ChargeAmountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ amount }) => {
      this.updateForm(amount);

      this.chargeAmountService
        .query({ filter: 'amount-is-null' })
        .pipe(
          map((res: HttpResponse<IChargeAmount[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IChargeAmount[]) => {
          if (!amount.walletChargeAmount || !amount.walletChargeAmount.id) {
            this.walletchargeamounts = resBody;
          } else {
            this.chargeAmountService
              .find(amount.walletChargeAmount.id)
              .pipe(
                map((subRes: HttpResponse<IChargeAmount>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IChargeAmount[]) => (this.walletchargeamounts = concatRes));
          }
        });
    });
  }

  updateForm(amount: IAmount): void {
    this.editForm.patchValue({
      id: amount.id,
      amount: amount.amount,
      netAmount: amount.netAmount,
      currency: amount.currency,
      purposeOfTransfer: amount.purposeOfTransfer,
      walletChargeAmount: amount.walletChargeAmount,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const amount = this.createFromForm();
    if (amount.id !== undefined) {
      this.subscribeToSaveResponse(this.amountService.update(amount));
    } else {
      this.subscribeToSaveResponse(this.amountService.create(amount));
    }
  }

  private createFromForm(): IAmount {
    return {
      ...new Amount(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      netAmount: this.editForm.get(['netAmount'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      purposeOfTransfer: this.editForm.get(['purposeOfTransfer'])!.value,
      walletChargeAmount: this.editForm.get(['walletChargeAmount'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmount>>): void {
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

  trackById(index: number, item: IChargeAmount): any {
    return item.id;
  }
}
