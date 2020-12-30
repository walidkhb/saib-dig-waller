import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CalculationInfoDetailsUpdateComponent } from 'app/entities/calculation-info-details/calculation-info-details-update.component';
import { CalculationInfoDetailsService } from 'app/entities/calculation-info-details/calculation-info-details.service';
import { CalculationInfoDetails } from 'app/shared/model/calculation-info-details.model';

describe('Component Tests', () => {
  describe('CalculationInfoDetails Management Update Component', () => {
    let comp: CalculationInfoDetailsUpdateComponent;
    let fixture: ComponentFixture<CalculationInfoDetailsUpdateComponent>;
    let service: CalculationInfoDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CalculationInfoDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CalculationInfoDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalculationInfoDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalculationInfoDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CalculationInfoDetails(123);
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
        const entity = new CalculationInfoDetails();
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
