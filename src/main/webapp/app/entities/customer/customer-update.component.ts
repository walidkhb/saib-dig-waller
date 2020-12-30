import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { ICustomerDetails } from 'app/shared/model/customer-details.model';
import { CustomerDetailsService } from 'app/entities/customer-details/customer-details.service';
import { ICustomerPreference } from 'app/shared/model/customer-preference.model';
import { CustomerPreferenceService } from 'app/entities/customer-preference/customer-preference.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IKYCInfo } from 'app/shared/model/kyc-info.model';
import { KYCInfoService } from 'app/entities/kyc-info/kyc-info.service';

type SelectableEntity = ICustomerDetails | ICustomerPreference | IAddress | IKYCInfo;

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html',
})
export class CustomerUpdateComponent implements OnInit {
  isSaving = false;
  customerdetails: ICustomerDetails[] = [];
  customerpreferences: ICustomerPreference[] = [];
  addresses: IAddress[] = [];
  kycinfos: IKYCInfo[] = [];

  editForm = this.fb.group({
    id: [],
    firstNameArabic: [],
    fatherNameArabic: [],
    grandFatherNameArabic: [],
    grandFatherNameEnglish: [],
    placeOfBirth: [],
    iDIssueDate: [],
    iDExpiryDate: [],
    maritalStatus: [],
    customerId: [],
    profileStatus: [],
    customerDetails: [],
    customerPreference: [],
    address: [],
    kycInfo: [],
  });

  constructor(
    protected customerService: CustomerService,
    protected customerDetailsService: CustomerDetailsService,
    protected customerPreferenceService: CustomerPreferenceService,
    protected addressService: AddressService,
    protected kYCInfoService: KYCInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);

      this.customerDetailsService
        .query({ filter: 'customer-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomerDetails[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomerDetails[]) => {
          if (!customer.customerDetails || !customer.customerDetails.id) {
            this.customerdetails = resBody;
          } else {
            this.customerDetailsService
              .find(customer.customerDetails.id)
              .pipe(
                map((subRes: HttpResponse<ICustomerDetails>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomerDetails[]) => (this.customerdetails = concatRes));
          }
        });

      this.customerPreferenceService
        .query({ filter: 'customer-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomerPreference[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomerPreference[]) => {
          if (!customer.customerPreference || !customer.customerPreference.id) {
            this.customerpreferences = resBody;
          } else {
            this.customerPreferenceService
              .find(customer.customerPreference.id)
              .pipe(
                map((subRes: HttpResponse<ICustomerPreference>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomerPreference[]) => (this.customerpreferences = concatRes));
          }
        });

      this.addressService
        .query({ filter: 'customer-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!customer.address || !customer.address.id) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(customer.address.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.addresses = concatRes));
          }
        });

      this.kYCInfoService
        .query({ filter: 'customer-is-null' })
        .pipe(
          map((res: HttpResponse<IKYCInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IKYCInfo[]) => {
          if (!customer.kycInfo || !customer.kycInfo.id) {
            this.kycinfos = resBody;
          } else {
            this.kYCInfoService
              .find(customer.kycInfo.id)
              .pipe(
                map((subRes: HttpResponse<IKYCInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IKYCInfo[]) => (this.kycinfos = concatRes));
          }
        });
    });
  }

  updateForm(customer: ICustomer): void {
    this.editForm.patchValue({
      id: customer.id,
      firstNameArabic: customer.firstNameArabic,
      fatherNameArabic: customer.fatherNameArabic,
      grandFatherNameArabic: customer.grandFatherNameArabic,
      grandFatherNameEnglish: customer.grandFatherNameEnglish,
      placeOfBirth: customer.placeOfBirth,
      iDIssueDate: customer.iDIssueDate,
      iDExpiryDate: customer.iDExpiryDate,
      maritalStatus: customer.maritalStatus,
      customerId: customer.customerId,
      profileStatus: customer.profileStatus,
      customerDetails: customer.customerDetails,
      customerPreference: customer.customerPreference,
      address: customer.address,
      kycInfo: customer.kycInfo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id'])!.value,
      firstNameArabic: this.editForm.get(['firstNameArabic'])!.value,
      fatherNameArabic: this.editForm.get(['fatherNameArabic'])!.value,
      grandFatherNameArabic: this.editForm.get(['grandFatherNameArabic'])!.value,
      grandFatherNameEnglish: this.editForm.get(['grandFatherNameEnglish'])!.value,
      placeOfBirth: this.editForm.get(['placeOfBirth'])!.value,
      iDIssueDate: this.editForm.get(['iDIssueDate'])!.value,
      iDExpiryDate: this.editForm.get(['iDExpiryDate'])!.value,
      maritalStatus: this.editForm.get(['maritalStatus'])!.value,
      customerId: this.editForm.get(['customerId'])!.value,
      profileStatus: this.editForm.get(['profileStatus'])!.value,
      customerDetails: this.editForm.get(['customerDetails'])!.value,
      customerPreference: this.editForm.get(['customerPreference'])!.value,
      address: this.editForm.get(['address'])!.value,
      kycInfo: this.editForm.get(['kycInfo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>): void {
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
