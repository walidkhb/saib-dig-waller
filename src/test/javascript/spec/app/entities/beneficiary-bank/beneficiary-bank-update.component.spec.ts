import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { BeneficiaryBankUpdateComponent } from 'app/entities/beneficiary-bank/beneficiary-bank-update.component';
import { BeneficiaryBankService } from 'app/entities/beneficiary-bank/beneficiary-bank.service';
import { BeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';

describe('Component Tests', () => {
  describe('BeneficiaryBank Management Update Component', () => {
    let comp: BeneficiaryBankUpdateComponent;
    let fixture: ComponentFixture<BeneficiaryBankUpdateComponent>;
    let service: BeneficiaryBankService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [BeneficiaryBankUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BeneficiaryBankUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeneficiaryBankUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeneficiaryBankService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BeneficiaryBank(123);
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
        const entity = new BeneficiaryBank();
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
