import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { FingerDetailsUpdateComponent } from 'app/entities/finger-details/finger-details-update.component';
import { FingerDetailsService } from 'app/entities/finger-details/finger-details.service';
import { FingerDetails } from 'app/shared/model/finger-details.model';

describe('Component Tests', () => {
  describe('FingerDetails Management Update Component', () => {
    let comp: FingerDetailsUpdateComponent;
    let fixture: ComponentFixture<FingerDetailsUpdateComponent>;
    let service: FingerDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [FingerDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FingerDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FingerDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FingerDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FingerDetails(123);
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
        const entity = new FingerDetails();
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
