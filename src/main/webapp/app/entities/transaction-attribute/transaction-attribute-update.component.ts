import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransactionAttribute, TransactionAttribute } from 'app/shared/model/transaction-attribute.model';
import { TransactionAttributeService } from './transaction-attribute.service';

@Component({
  selector: 'jhi-transaction-attribute-update',
  templateUrl: './transaction-attribute-update.component.html',
})
export class TransactionAttributeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    narativeLine1: [],
    narativeLine2: [],
    narativeLine3: [],
    narativeLine4: [],
    clientRefNumber: [],
  });

  constructor(
    protected transactionAttributeService: TransactionAttributeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionAttribute }) => {
      this.updateForm(transactionAttribute);
    });
  }

  updateForm(transactionAttribute: ITransactionAttribute): void {
    this.editForm.patchValue({
      id: transactionAttribute.id,
      narativeLine1: transactionAttribute.narativeLine1,
      narativeLine2: transactionAttribute.narativeLine2,
      narativeLine3: transactionAttribute.narativeLine3,
      narativeLine4: transactionAttribute.narativeLine4,
      clientRefNumber: transactionAttribute.clientRefNumber,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionAttribute = this.createFromForm();
    if (transactionAttribute.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionAttributeService.update(transactionAttribute));
    } else {
      this.subscribeToSaveResponse(this.transactionAttributeService.create(transactionAttribute));
    }
  }

  private createFromForm(): ITransactionAttribute {
    return {
      ...new TransactionAttribute(),
      id: this.editForm.get(['id'])!.value,
      narativeLine1: this.editForm.get(['narativeLine1'])!.value,
      narativeLine2: this.editForm.get(['narativeLine2'])!.value,
      narativeLine3: this.editForm.get(['narativeLine3'])!.value,
      narativeLine4: this.editForm.get(['narativeLine4'])!.value,
      clientRefNumber: this.editForm.get(['clientRefNumber'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionAttribute>>): void {
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
