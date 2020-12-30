import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBeneficiary, Beneficiary } from 'app/shared/model/beneficiary.model';
import { BeneficiaryService } from './beneficiary.service';
import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { PaymentDetailsService } from 'app/entities/payment-details/payment-details.service';
import { IBeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';
import { BeneficiaryBankService } from 'app/entities/beneficiary-bank/beneficiary-bank.service';
import { IAccountScheme } from 'app/shared/model/account-scheme.model';
import { AccountSchemeService } from 'app/entities/account-scheme/account-scheme.service';

type SelectableEntity = IPaymentDetails | IBeneficiaryBank | IAccountScheme;

@Component({
  selector: 'jhi-beneficiary-update',
  templateUrl: './beneficiary-update.component.html',
})
export class BeneficiaryUpdateComponent implements OnInit {
  isSaving = false;
  beneficiarydetails: IPaymentDetails[] = [];
  beneficiarybanks: IBeneficiaryBank[] = [];
  beneficiaryaccounts: IAccountScheme[] = [];

  editForm = this.fb.group({
    id: [],
    nickName: [],
    firstName: [],
    lastName: [],
    middleName: [],
    beneficiaryId: [],
    beneficiaryType: [],
    address: [],
    nationality: [],
    telephone: [],
    dateOfBirth: [],
    iDNumber: [],
    iDType: [],
    beneficiaryRelation: [],
    beneficiaryCity: [],
    beneficiaryPhoneCountryCode: [],
    beneficiarySourceOfFund: [],
    beneficiaryZipCode: [],
    beneficiaryStatus: [],
    beneficiaryCurrency: [],
    beneficiaryDetails: [],
    beneficiaryBank: [],
    beneficiaryAccount: [],
  });

  constructor(
    protected beneficiaryService: BeneficiaryService,
    protected paymentDetailsService: PaymentDetailsService,
    protected beneficiaryBankService: BeneficiaryBankService,
    protected accountSchemeService: AccountSchemeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beneficiary }) => {
      this.updateForm(beneficiary);

      this.paymentDetailsService
        .query({ filter: 'beneficiary-is-null' })
        .pipe(
          map((res: HttpResponse<IPaymentDetails[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPaymentDetails[]) => {
          if (!beneficiary.beneficiaryDetails || !beneficiary.beneficiaryDetails.id) {
            this.beneficiarydetails = resBody;
          } else {
            this.paymentDetailsService
              .find(beneficiary.beneficiaryDetails.id)
              .pipe(
                map((subRes: HttpResponse<IPaymentDetails>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPaymentDetails[]) => (this.beneficiarydetails = concatRes));
          }
        });

      this.beneficiaryBankService
        .query({ filter: 'beneficiary-is-null' })
        .pipe(
          map((res: HttpResponse<IBeneficiaryBank[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBeneficiaryBank[]) => {
          if (!beneficiary.beneficiaryBank || !beneficiary.beneficiaryBank.id) {
            this.beneficiarybanks = resBody;
          } else {
            this.beneficiaryBankService
              .find(beneficiary.beneficiaryBank.id)
              .pipe(
                map((subRes: HttpResponse<IBeneficiaryBank>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBeneficiaryBank[]) => (this.beneficiarybanks = concatRes));
          }
        });

      this.accountSchemeService
        .query({ filter: 'beneficiary-is-null' })
        .pipe(
          map((res: HttpResponse<IAccountScheme[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAccountScheme[]) => {
          if (!beneficiary.beneficiaryAccount || !beneficiary.beneficiaryAccount.id) {
            this.beneficiaryaccounts = resBody;
          } else {
            this.accountSchemeService
              .find(beneficiary.beneficiaryAccount.id)
              .pipe(
                map((subRes: HttpResponse<IAccountScheme>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAccountScheme[]) => (this.beneficiaryaccounts = concatRes));
          }
        });
    });
  }

  updateForm(beneficiary: IBeneficiary): void {
    this.editForm.patchValue({
      id: beneficiary.id,
      nickName: beneficiary.nickName,
      firstName: beneficiary.firstName,
      lastName: beneficiary.lastName,
      middleName: beneficiary.middleName,
      beneficiaryId: beneficiary.beneficiaryId,
      beneficiaryType: beneficiary.beneficiaryType,
      address: beneficiary.address,
      nationality: beneficiary.nationality,
      telephone: beneficiary.telephone,
      dateOfBirth: beneficiary.dateOfBirth,
      iDNumber: beneficiary.iDNumber,
      iDType: beneficiary.iDType,
      beneficiaryRelation: beneficiary.beneficiaryRelation,
      beneficiaryCity: beneficiary.beneficiaryCity,
      beneficiaryPhoneCountryCode: beneficiary.beneficiaryPhoneCountryCode,
      beneficiarySourceOfFund: beneficiary.beneficiarySourceOfFund,
      beneficiaryZipCode: beneficiary.beneficiaryZipCode,
      beneficiaryStatus: beneficiary.beneficiaryStatus,
      beneficiaryCurrency: beneficiary.beneficiaryCurrency,
      beneficiaryDetails: beneficiary.beneficiaryDetails,
      beneficiaryBank: beneficiary.beneficiaryBank,
      beneficiaryAccount: beneficiary.beneficiaryAccount,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const beneficiary = this.createFromForm();
    if (beneficiary.id !== undefined) {
      this.subscribeToSaveResponse(this.beneficiaryService.update(beneficiary));
    } else {
      this.subscribeToSaveResponse(this.beneficiaryService.create(beneficiary));
    }
  }

  private createFromForm(): IBeneficiary {
    return {
      ...new Beneficiary(),
      id: this.editForm.get(['id'])!.value,
      nickName: this.editForm.get(['nickName'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      beneficiaryId: this.editForm.get(['beneficiaryId'])!.value,
      beneficiaryType: this.editForm.get(['beneficiaryType'])!.value,
      address: this.editForm.get(['address'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      iDNumber: this.editForm.get(['iDNumber'])!.value,
      iDType: this.editForm.get(['iDType'])!.value,
      beneficiaryRelation: this.editForm.get(['beneficiaryRelation'])!.value,
      beneficiaryCity: this.editForm.get(['beneficiaryCity'])!.value,
      beneficiaryPhoneCountryCode: this.editForm.get(['beneficiaryPhoneCountryCode'])!.value,
      beneficiarySourceOfFund: this.editForm.get(['beneficiarySourceOfFund'])!.value,
      beneficiaryZipCode: this.editForm.get(['beneficiaryZipCode'])!.value,
      beneficiaryStatus: this.editForm.get(['beneficiaryStatus'])!.value,
      beneficiaryCurrency: this.editForm.get(['beneficiaryCurrency'])!.value,
      beneficiaryDetails: this.editForm.get(['beneficiaryDetails'])!.value,
      beneficiaryBank: this.editForm.get(['beneficiaryBank'])!.value,
      beneficiaryAccount: this.editForm.get(['beneficiaryAccount'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeneficiary>>): void {
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
