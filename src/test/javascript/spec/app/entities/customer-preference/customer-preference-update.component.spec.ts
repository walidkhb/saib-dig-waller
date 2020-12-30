import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CustomerPreferenceUpdateComponent } from 'app/entities/customer-preference/customer-preference-update.component';
import { CustomerPreferenceService } from 'app/entities/customer-preference/customer-preference.service';
import { CustomerPreference } from 'app/shared/model/customer-preference.model';

describe('Component Tests', () => {
  describe('CustomerPreference Management Update Component', () => {
    let comp: CustomerPreferenceUpdateComponent;
    let fixture: ComponentFixture<CustomerPreferenceUpdateComponent>;
    let service: CustomerPreferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CustomerPreferenceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CustomerPreferenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerPreferenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerPreferenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerPreference(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerPreference();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
