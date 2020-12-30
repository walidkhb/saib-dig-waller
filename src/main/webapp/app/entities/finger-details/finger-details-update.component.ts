import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFingerDetails, FingerDetails } from 'app/shared/model/finger-details.model';
import { FingerDetailsService } from './finger-details.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-finger-details-update',
  templateUrl: './finger-details-update.component.html',
})
export class FingerDetailsUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    fingerPrint: [],
    fingerIndex: [],
    customer: [],
  });

  constructor(
    protected fingerDetailsService: FingerDetailsService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fingerDetails }) => {
      this.updateForm(fingerDetails);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(fingerDetails: IFingerDetails): void {
    this.editForm.patchValue({
      id: fingerDetails.id,
      fingerPrint: fingerDetails.fingerPrint,
      fingerIndex: fingerDetails.fingerIndex,
      customer: fingerDetails.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fingerDetails = this.createFromForm();
    if (fingerDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.fingerDetailsService.update(fingerDetails));
    } else {
      this.subscribeToSaveResponse(this.fingerDetailsService.create(fingerDetails));
    }
  }

  private createFromForm(): IFingerDetails {
    return {
      ...new FingerDetails(),
      id: this.editForm.get(['id'])!.value,
      fingerPrint: this.editForm.get(['fingerPrint'])!.value,
      fingerIndex: this.editForm.get(['fingerIndex'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFingerDetails>>): void {
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

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
