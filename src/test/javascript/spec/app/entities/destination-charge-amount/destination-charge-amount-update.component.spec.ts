import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { DestinationChargeAmountUpdateComponent } from 'app/entities/destination-charge-amount/destination-charge-amount-update.component';
import { DestinationChargeAmountService } from 'app/entities/destination-charge-amount/destination-charge-amount.service';
import { DestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';

describe('Component Tests', () => {
  describe('DestinationChargeAmount Management Update Component', () => {
    let comp: DestinationChargeAmountUpdateComponent;
    let fixture: ComponentFixture<DestinationChargeAmountUpdateComponent>;
    let service: DestinationChargeAmountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [DestinationChargeAmountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DestinationChargeAmountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DestinationChargeAmountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DestinationChargeAmountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DestinationChargeAmount(123);
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
        const entity = new DestinationChargeAmount();
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
