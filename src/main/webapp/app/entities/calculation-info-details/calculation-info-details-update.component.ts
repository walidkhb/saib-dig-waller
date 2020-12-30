import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICalculationInfoDetails, CalculationInfoDetails } from 'app/shared/model/calculation-info-details.model';
import { CalculationInfoDetailsService } from './calculation-info-details.service';
import { ITransactionInfo } from 'app/shared/model/transaction-info.model';
import { TransactionInfoService } from 'app/entities/transaction-info/transaction-info.service';
import { IChargeAmount } from 'app/shared/model/charge-amount.model';
import { ChargeAmountService } from 'app/entities/charge-amount/charge-amount.service';
import { IDestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';
import { DestinationChargeAmountService } from 'app/entities/destination-charge-amount/destination-charge-amount.service';

type SelectableEntity = ITransactionInfo | IChargeAmount | IDestinationChargeAmount;

@Component({
  selector: 'jhi-calculation-info-details-update',
  templateUrl: './calculation-info-details-update.component.html',
})
export class CalculationInfoDetailsUpdateComponent implements OnInit {
  isSaving = false;
  transcalculations: ITransactionInfo[] = [];
  chargeamounts: IChargeAmount[] = [];
  destchargeamounts: IDestinationChargeAmount[] = [];

  editForm = this.fb.group({
    id: [],
    totalDebitAmount: [],
    destinationAmount: [],
    destinationExchangeRate: [],
    destinationCurrencyIndicator: [],
    discountAmount: [],
    transCalculation: [],
    chargeAmount: [],
    destChargeAmount: [],
  });

  constructor(
    protected calculationInfoDetailsService: CalculationInfoDetailsService,
    protected transactionInfoService: TransactionInfoService,
    protected chargeAmountService: ChargeAmountService,
    protected destinationChargeAmountService: DestinationChargeAmountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calculationInfoDetails }) => {
      this.updateForm(calculationInfoDetails);

      this.transactionInfoService
        .query({ filter: 'calculationinfodetails-is-null' })
        .pipe(
          map((res: HttpResponse<ITransactionInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITransactionInfo[]) => {
          if (!calculationInfoDetails.transCalculation || !calculationInfoDetails.transCalculation.id) {
            this.transcalculations = resBody;
          } else {
            this.transactionInfoService
              .find(calculationInfoDetails.transCalculation.id)
              .pipe(
                map((subRes: HttpResponse<ITransactionInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITransactionInfo[]) => (this.transcalculations = concatRes));
          }
        });

      this.chargeAmountService
        .query({ filter: 'calculationinfodetails-is-null' })
        .pipe(
          map((res: HttpResponse<IChargeAmount[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IChargeAmount[]) => {
          if (!calculationInfoDetails.chargeAmount || !calculationInfoDetails.chargeAmount.id) {
            this.chargeamounts = resBody;
          } else {
            this.chargeAmountService
              .find(calculationInfoDetails.chargeAmount.id)
              .pipe(
                map((subRes: HttpResponse<IChargeAmount>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IChargeAmount[]) => (this.chargeamounts = concatRes));
          }
        });

      this.destinationChargeAmountService
        .query({ filter: 'calculationinfodetails-is-null' })
        .pipe(
          map((res: HttpResponse<IDestinationChargeAmount[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDestinationChargeAmount[]) => {
          if (!calculationInfoDetails.destChargeAmount || !calculationInfoDetails.destChargeAmount.id) {
            this.destchargeamounts = resBody;
          } else {
            this.destinationChargeAmountService
              .find(calculationInfoDetails.destChargeAmount.id)
              .pipe(
                map((subRes: HttpResponse<IDestinationChargeAmount>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDestinationChargeAmount[]) => (this.destchargeamounts = concatRes));
          }
        });
    });
  }

  updateForm(calculationInfoDetails: ICalculationInfoDetails): void {
    this.editForm.patchValue({
      id: calculationInfoDetails.id,
      totalDebitAmount: calculationInfoDetails.totalDebitAmount,
      destinationAmount: calculationInfoDetails.destinationAmount,
      destinationExchangeRate: calculationInfoDetails.destinationExchangeRate,
      destinationCurrencyIndicator: calculationInfoDetails.destinationCurrencyIndicator,
      discountAmount: calculationInfoDetails.discountAmount,
      transCalculation: calculationInfoDetails.transCalculation,
      chargeAmount: calculationInfoDetails.chargeAmount,
      destChargeAmount: calculationInfoDetails.destChargeAmount,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calculationInfoDetails = this.createFromForm();
    if (calculationInfoDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.calculationInfoDetailsService.update(calculationInfoDetails));
    } else {
      this.subscribeToSaveResponse(this.calculationInfoDetailsService.create(calculationInfoDetails));
    }
  }

  private createFromForm(): ICalculationInfoDetails {
    return {
      ...new CalculationInfoDetails(),
      id: this.editForm.get(['id'])!.value,
      totalDebitAmount: this.editForm.get(['totalDebitAmount'])!.value,
      destinationAmount: this.editForm.get(['destinationAmount'])!.value,
      destinationExchangeRate: this.editForm.get(['destinationExchangeRate'])!.value,
      destinationCurrencyIndicator: this.editForm.get(['destinationCurrencyIndicator'])!.value,
      discountAmount: this.editForm.get(['discountAmount'])!.value,
      transCalculation: this.editForm.get(['transCalculation'])!.value,
      chargeAmount: this.editForm.get(['chargeAmount'])!.value,
      destChargeAmount: this.editForm.get(['destChargeAmount'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalculationInfoDetails>>): void {
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
