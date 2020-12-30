import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDestinationChargeAmount, DestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';
import { DestinationChargeAmountService } from './destination-charge-amount.service';

@Component({
  selector: 'jhi-destination-charge-amount-update',
  templateUrl: './destination-charge-amount-update.component.html',
})
export class DestinationChargeAmountUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    vATEstimated: [],
    amountEstimated: [],
  });

  constructor(
    protected destinationChargeAmountService: DestinationChargeAmountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ destinationChargeAmount }) => {
      this.updateForm(destinationChargeAmount);
    });
  }

  updateForm(destinationChargeAmount: IDestinationChargeAmount): void {
    this.editForm.patchValue({
      id: destinationChargeAmount.id,
      vATEstimated: destinationChargeAmount.vATEstimated,
      amountEstimated: destinationChargeAmount.amountEstimated,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const destinationChargeAmount = this.createFromForm();
    if (destinationChargeAmount.id !== undefined) {
      this.subscribeToSaveResponse(this.destinationChargeAmountService.update(destinationChargeAmount));
    } else {
      this.subscribeToSaveResponse(this.destinationChargeAmountService.create(destinationChargeAmount));
    }
  }

  private createFromForm(): IDestinationChargeAmount {
    return {
      ...new DestinationChargeAmount(),
      id: this.editForm.get(['id'])!.value,
      vATEstimated: this.editForm.get(['vATEstimated'])!.value,
      amountEstimated: this.editForm.get(['amountEstimated'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDestinationChargeAmount>>): void {
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
