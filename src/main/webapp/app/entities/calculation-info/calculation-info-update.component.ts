import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICalculationInfo, CalculationInfo } from 'app/shared/model/calculation-info.model';
import { CalculationInfoService } from './calculation-info.service';

@Component({
  selector: 'jhi-calculation-info-update',
  templateUrl: './calculation-info-update.component.html',
})
export class CalculationInfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    customerId: [],
    beneficiaryId: [],
    fromCurrency: [],
    toCurrency: [],
    transactionAmount: [],
    transactionCurrency: [],
  });

  constructor(
    protected calculationInfoService: CalculationInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calculationInfo }) => {
      this.updateForm(calculationInfo);
    });
  }

  updateForm(calculationInfo: ICalculationInfo): void {
    this.editForm.patchValue({
      id: calculationInfo.id,
      customerId: calculationInfo.customerId,
      beneficiaryId: calculationInfo.beneficiaryId,
      fromCurrency: calculationInfo.fromCurrency,
      toCurrency: calculationInfo.toCurrency,
      transactionAmount: calculationInfo.transactionAmount,
      transactionCurrency: calculationInfo.transactionCurrency,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calculationInfo = this.createFromForm();
    if (calculationInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.calculationInfoService.update(calculationInfo));
    } else {
      this.subscribeToSaveResponse(this.calculationInfoService.create(calculationInfo));
    }
  }

  private createFromForm(): ICalculationInfo {
    return {
      ...new CalculationInfo(),
      id: this.editForm.get(['id'])!.value,
      customerId: this.editForm.get(['customerId'])!.value,
      beneficiaryId: this.editForm.get(['beneficiaryId'])!.value,
      fromCurrency: this.editForm.get(['fromCurrency'])!.value,
      toCurrency: this.editForm.get(['toCurrency'])!.value,
      transactionAmount: this.editForm.get(['transactionAmount'])!.value,
      transactionCurrency: this.editForm.get(['transactionCurrency'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalculationInfo>>): void {
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
