import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CalculationInfoUpdateComponent } from 'app/entities/calculation-info/calculation-info-update.component';
import { CalculationInfoService } from 'app/entities/calculation-info/calculation-info.service';
import { CalculationInfo } from 'app/shared/model/calculation-info.model';

describe('Component Tests', () => {
  describe('CalculationInfo Management Update Component', () => {
    let comp: CalculationInfoUpdateComponent;
    let fixture: ComponentFixture<CalculationInfoUpdateComponent>;
    let service: CalculationInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CalculationInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CalculationInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalculationInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalculationInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CalculationInfo(123);
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
        const entity = new CalculationInfo();
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
