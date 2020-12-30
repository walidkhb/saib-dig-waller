import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomerDetails, CustomerDetails } from 'app/shared/model/customer-details.model';
import { CustomerDetailsService } from './customer-details.service';

@Component({
  selector: 'jhi-customer-details-update',
  templateUrl: './customer-details-update.component.html',
})
export class CustomerDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nationalIdentityNumber: [],
    idType: [],
    dateOfBirth: [],
    mobilePhoneNumber: [null, [Validators.pattern('[+]\\d+-\\d+')]],
    agentVerificationNumber: [],
  });

  constructor(
    protected customerDetailsService: CustomerDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerDetails }) => {
      this.updateForm(customerDetails);
    });
  }

  updateForm(customerDetails: ICustomerDetails): void {
    this.editForm.patchValue({
      id: customerDetails.id,
      nationalIdentityNumber: customerDetails.nationalIdentityNumber,
      idType: customerDetails.idType,
      dateOfBirth: customerDetails.dateOfBirth,
      mobilePhoneNumber: customerDetails.mobilePhoneNumber,
      agentVerificationNumber: customerDetails.agentVerificationNumber,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerDetails = this.createFromForm();
    if (customerDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.customerDetailsService.update(customerDetails));
    } else {
      this.subscribeToSaveResponse(this.customerDetailsService.create(customerDetails));
    }
  }

  private createFromForm(): ICustomerDetails {
    return {
      ...new CustomerDetails(),
      id: this.editForm.get(['id'])!.value,
      nationalIdentityNumber: this.editForm.get(['nationalIdentityNumber'])!.value,
      idType: this.editForm.get(['idType'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      mobilePhoneNumber: this.editForm.get(['mobilePhoneNumber'])!.value,
      agentVerificationNumber: this.editForm.get(['agentVerificationNumber'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerDetails>>): void {
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
