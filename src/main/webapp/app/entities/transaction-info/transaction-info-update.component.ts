import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITransactionInfo, TransactionInfo } from 'app/shared/model/transaction-info.model';
import { TransactionInfoService } from './transaction-info.service';
import { ITransactionAttribute } from 'app/shared/model/transaction-attribute.model';
import { TransactionAttributeService } from 'app/entities/transaction-attribute/transaction-attribute.service';

@Component({
  selector: 'jhi-transaction-info-update',
  templateUrl: './transaction-info-update.component.html',
})
export class TransactionInfoUpdateComponent implements OnInit {
  isSaving = false;
  transactionattrs: ITransactionAttribute[] = [];

  editForm = this.fb.group({
    id: [],
    transactionType: [],
    transactionId: [],
    creditDebitIndicator: [],
    creationDateTime: [],
    status: [],
    transactionAttr: [],
  });

  constructor(
    protected transactionInfoService: TransactionInfoService,
    protected transactionAttributeService: TransactionAttributeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionInfo }) => {
      this.updateForm(transactionInfo);

      this.transactionAttributeService
        .query({ filter: 'transactioninfo-is-null' })
        .pipe(
          map((res: HttpResponse<ITransactionAttribute[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITransactionAttribute[]) => {
          if (!transactionInfo.transactionAttr || !transactionInfo.transactionAttr.id) {
            this.transactionattrs = resBody;
          } else {
            this.transactionAttributeService
              .find(transactionInfo.transactionAttr.id)
              .pipe(
                map((subRes: HttpResponse<ITransactionAttribute>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITransactionAttribute[]) => (this.transactionattrs = concatRes));
          }
        });
    });
  }

  updateForm(transactionInfo: ITransactionInfo): void {
    this.editForm.patchValue({
      id: transactionInfo.id,
      transactionType: transactionInfo.transactionType,
      transactionId: transactionInfo.transactionId,
      creditDebitIndicator: transactionInfo.creditDebitIndicator,
      creationDateTime: transactionInfo.creationDateTime,
      status: transactionInfo.status,
      transactionAttr: transactionInfo.transactionAttr,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionInfo = this.createFromForm();
    if (transactionInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionInfoService.update(transactionInfo));
    } else {
      this.subscribeToSaveResponse(this.transactionInfoService.create(transactionInfo));
    }
  }

  private createFromForm(): ITransactionInfo {
    return {
      ...new TransactionInfo(),
      id: this.editForm.get(['id'])!.value,
      transactionType: this.editForm.get(['transactionType'])!.value,
      transactionId: this.editForm.get(['transactionId'])!.value,
      creditDebitIndicator: this.editForm.get(['creditDebitIndicator'])!.value,
      creationDateTime: this.editForm.get(['creationDateTime'])!.value,
      status: this.editForm.get(['status'])!.value,
      transactionAttr: this.editForm.get(['transactionAttr'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionInfo>>): void {
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

  trackById(index: number, item: ITransactionAttribute): any {
    return item.id;
  }
}
