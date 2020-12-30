import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { AmountUpdateComponent } from 'app/entities/amount/amount-update.component';
import { AmountService } from 'app/entities/amount/amount.service';
import { Amount } from 'app/shared/model/amount.model';

describe('Component Tests', () => {
  describe('Amount Management Update Component', () => {
    let comp: AmountUpdateComponent;
    let fixture: ComponentFixture<AmountUpdateComponent>;
    let service: AmountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [AmountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AmountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Amount(123);
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
        const entity = new Amount();
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
