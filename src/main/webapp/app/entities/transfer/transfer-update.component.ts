import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITransfer, Transfer } from 'app/shared/model/transfer.model';
import { TransferService } from './transfer.service';
import { IDebtor } from 'app/shared/model/debtor.model';
import { DebtorService } from 'app/entities/debtor/debtor.service';
import { ICreditor } from 'app/shared/model/creditor.model';
import { CreditorService } from 'app/entities/creditor/creditor.service';
import { IBeneficiary } from 'app/shared/model/beneficiary.model';
import { BeneficiaryService } from 'app/entities/beneficiary/beneficiary.service';
import { ITransactionInfo } from 'app/shared/model/transaction-info.model';
import { TransactionInfoService } from 'app/entities/transaction-info/transaction-info.service';

type SelectableEntity = IDebtor | ICreditor | IBeneficiary | ITransactionInfo;

@Component({
  selector: 'jhi-transfer-update',
  templateUrl: './transfer-update.component.html',
})
export class TransferUpdateComponent implements OnInit {
  isSaving = false;
  debtdetails: IDebtor[] = [];
  creddetails: ICreditor[] = [];
  beneficiaryinfos: IBeneficiary[] = [];
  transactioninfos: ITransactionInfo[] = [];

  editForm = this.fb.group({
    id: [],
    debtDetails: [],
    credDetails: [],
    beneficiaryInfo: [],
    transactionInfo: [],
  });

  constructor(
    protected transferService: TransferService,
    protected debtorService: DebtorService,
    protected creditorService: CreditorService,
    protected beneficiaryService: BeneficiaryService,
    protected transactionInfoService: TransactionInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transfer }) => {
      this.updateForm(transfer);

      this.debtorService
        .query({ filter: 'transfer-is-null' })
        .pipe(
          map((res: HttpResponse<IDebtor[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDebtor[]) => {
          if (!transfer.debtDetails || !transfer.debtDetails.id) {
            this.debtdetails = resBody;
          } else {
            this.debtorService
              .find(transfer.debtDetails.id)
              .pipe(
                map((subRes: HttpResponse<IDebtor>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDebtor[]) => (this.debtdetails = concatRes));
          }
        });

      this.creditorService
        .query({ filter: 'transfer-is-null' })
        .pipe(
          map((res: HttpResponse<ICreditor[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICreditor[]) => {
          if (!transfer.credDetails || !transfer.credDetails.id) {
            this.creddetails = resBody;
          } else {
            this.creditorService
              .find(transfer.credDetails.id)
              .pipe(
                map((subRes: HttpResponse<ICreditor>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICreditor[]) => (this.creddetails = concatRes));
          }
        });

      this.beneficiaryService
        .query({ filter: 'transfer-is-null' })
        .pipe(
          map((res: HttpResponse<IBeneficiary[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBeneficiary[]) => {
          if (!transfer.beneficiaryInfo || !transfer.beneficiaryInfo.id) {
            this.beneficiaryinfos = resBody;
          } else {
            this.beneficiaryService
              .find(transfer.beneficiaryInfo.id)
              .pipe(
                map((subRes: HttpResponse<IBeneficiary>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBeneficiary[]) => (this.beneficiaryinfos = concatRes));
          }
        });

      this.transactionInfoService
        .query({ filter: 'transfer-is-null' })
        .pipe(
          map((res: HttpResponse<ITransactionInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITransactionInfo[]) => {
          if (!transfer.transactionInfo || !transfer.transactionInfo.id) {
            this.transactioninfos = resBody;
          } else {
            this.transactionInfoService
              .find(transfer.transactionInfo.id)
              .pipe(
                map((subRes: HttpResponse<ITransactionInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITransactionInfo[]) => (this.transactioninfos = concatRes));
          }
        });
    });
  }

  updateForm(transfer: ITransfer): void {
    this.editForm.patchValue({
      id: transfer.id,
      debtDetails: transfer.debtDetails,
      credDetails: transfer.credDetails,
      beneficiaryInfo: transfer.beneficiaryInfo,
      transactionInfo: transfer.transactionInfo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transfer = this.createFromForm();
    if (transfer.id !== undefined) {
      this.subscribeToSaveResponse(this.transferService.update(transfer));
    } else {
      this.subscribeToSaveResponse(this.transferService.create(transfer));
    }
  }

  private createFromForm(): ITransfer {
    return {
      ...new Transfer(),
      id: this.editForm.get(['id'])!.value,
      debtDetails: this.editForm.get(['debtDetails'])!.value,
      credDetails: this.editForm.get(['credDetails'])!.value,
      beneficiaryInfo: this.editForm.get(['beneficiaryInfo'])!.value,
      transactionInfo: this.editForm.get(['transactionInfo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransfer>>): void {
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
