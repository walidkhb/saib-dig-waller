import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KYCInfoUpdateComponent } from 'app/entities/kyc-info/kyc-info-update.component';
import { KYCInfoService } from 'app/entities/kyc-info/kyc-info.service';
import { KYCInfo } from 'app/shared/model/kyc-info.model';

describe('Component Tests', () => {
  describe('KYCInfo Management Update Component', () => {
    let comp: KYCInfoUpdateComponent;
    let fixture: ComponentFixture<KYCInfoUpdateComponent>;
    let service: KYCInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KYCInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KYCInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KYCInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KYCInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new KYCInfo(123);
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
        const entity = new KYCInfo();
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
