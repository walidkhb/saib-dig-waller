import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBeneficiaryBank, BeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';
import { BeneficiaryBankService } from './beneficiary-bank.service';

@Component({
  selector: 'jhi-beneficiary-bank-update',
  templateUrl: './beneficiary-bank-update.component.html',
})
export class BeneficiaryBankUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    bankCode: [],
    bankName: [],
    bankCountry: [],
    bankBranchCode: [],
    branchNameAndAddress: [],
  });

  constructor(
    protected beneficiaryBankService: BeneficiaryBankService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beneficiaryBank }) => {
      this.updateForm(beneficiaryBank);
    });
  }

  updateForm(beneficiaryBank: IBeneficiaryBank): void {
    this.editForm.patchValue({
      id: beneficiaryBank.id,
      bankCode: beneficiaryBank.bankCode,
      bankName: beneficiaryBank.bankName,
      bankCountry: beneficiaryBank.bankCountry,
      bankBranchCode: beneficiaryBank.bankBranchCode,
      branchNameAndAddress: beneficiaryBank.branchNameAndAddress,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const beneficiaryBank = this.createFromForm();
    if (beneficiaryBank.id !== undefined) {
      this.subscribeToSaveResponse(this.beneficiaryBankService.update(beneficiaryBank));
    } else {
      this.subscribeToSaveResponse(this.beneficiaryBankService.create(beneficiaryBank));
    }
  }

  private createFromForm(): IBeneficiaryBank {
    return {
      ...new BeneficiaryBank(),
      id: this.editForm.get(['id'])!.value,
      bankCode: this.editForm.get(['bankCode'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      bankCountry: this.editForm.get(['bankCountry'])!.value,
      bankBranchCode: this.editForm.get(['bankBranchCode'])!.value,
      branchNameAndAddress: this.editForm.get(['branchNameAndAddress'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeneficiaryBank>>): void {
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
