import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChargeAmount, ChargeAmount } from 'app/shared/model/charge-amount.model';
import { ChargeAmountService } from './charge-amount.service';

@Component({
  selector: 'jhi-charge-amount-update',
  templateUrl: './charge-amount-update.component.html',
})
export class ChargeAmountUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    amount: [],
    currency: [],
    vAT: [],
  });

  constructor(protected chargeAmountService: ChargeAmountService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chargeAmount }) => {
      this.updateForm(chargeAmount);
    });
  }

  updateForm(chargeAmount: IChargeAmount): void {
    this.editForm.patchValue({
      id: chargeAmount.id,
      amount: chargeAmount.amount,
      currency: chargeAmount.currency,
      vAT: chargeAmount.vAT,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chargeAmount = this.createFromForm();
    if (chargeAmount.id !== undefined) {
      this.subscribeToSaveResponse(this.chargeAmountService.update(chargeAmount));
    } else {
      this.subscribeToSaveResponse(this.chargeAmountService.create(chargeAmount));
    }
  }

  private createFromForm(): IChargeAmount {
    return {
      ...new ChargeAmount(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      vAT: this.editForm.get(['vAT'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChargeAmount>>): void {
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
}
