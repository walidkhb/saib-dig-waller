import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { ChargeAmountUpdateComponent } from 'app/entities/charge-amount/charge-amount-update.component';
import { ChargeAmountService } from 'app/entities/charge-amount/charge-amount.service';
import { ChargeAmount } from 'app/shared/model/charge-amount.model';

describe('Component Tests', () => {
  describe('ChargeAmount Management Update Component', () => {
    let comp: ChargeAmountUpdateComponent;
    let fixture: ComponentFixture<ChargeAmountUpdateComponent>;
    let service: ChargeAmountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [ChargeAmountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ChargeAmountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChargeAmountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChargeAmountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ChargeAmount(123);
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
        const entity = new ChargeAmount();
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
