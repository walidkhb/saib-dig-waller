import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomerPreference, CustomerPreference } from 'app/shared/model/customer-preference.model';
import { CustomerPreferenceService } from './customer-preference.service';

@Component({
  selector: 'jhi-customer-preference-update',
  templateUrl: './customer-preference-update.component.html',
})
export class CustomerPreferenceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    email: [null, [Validators.pattern('[^@]+@[^\\.]+\\..+$')]],
    language: [],
  });

  constructor(
    protected customerPreferenceService: CustomerPreferenceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerPreference }) => {
      this.updateForm(customerPreference);
    });
  }

  updateForm(customerPreference: ICustomerPreference): void {
    this.editForm.patchValue({
      id: customerPreference.id,
      email: customerPreference.email,
      language: customerPreference.language,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerPreference = this.createFromForm();
    if (customerPreference.id !== undefined) {
      this.subscribeToSaveResponse(this.customerPreferenceService.update(customerPreference));
    } else {
      this.subscribeToSaveResponse(this.customerPreferenceService.create(customerPreference));
    }
  }

  private createFromForm(): ICustomerPreference {
    return {
      ...new CustomerPreference(),
      id: this.editForm.get(['id'])!.value,
      email: this.editForm.get(['email'])!.value,
      language: this.editForm.get(['language'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerPreference>>): void {
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
