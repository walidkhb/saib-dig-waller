import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TransactionDetailsUpdateComponent } from 'app/entities/transaction-details/transaction-details-update.component';
import { TransactionDetailsService } from 'app/entities/transaction-details/transaction-details.service';
import { TransactionDetails } from 'app/shared/model/transaction-details.model';

describe('Component Tests', () => {
  describe('TransactionDetails Management Update Component', () => {
    let comp: TransactionDetailsUpdateComponent;
    let fixture: ComponentFixture<TransactionDetailsUpdateComponent>;
    let service: TransactionDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TransactionDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TransactionDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionDetails(123);
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
        const entity = new TransactionDetails();
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
