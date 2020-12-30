import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TransactionAttributeUpdateComponent } from 'app/entities/transaction-attribute/transaction-attribute-update.component';
import { TransactionAttributeService } from 'app/entities/transaction-attribute/transaction-attribute.service';
import { TransactionAttribute } from 'app/shared/model/transaction-attribute.model';

describe('Component Tests', () => {
  describe('TransactionAttribute Management Update Component', () => {
    let comp: TransactionAttributeUpdateComponent;
    let fixture: ComponentFixture<TransactionAttributeUpdateComponent>;
    let service: TransactionAttributeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TransactionAttributeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TransactionAttributeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionAttributeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionAttributeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionAttribute(123);
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
        const entity = new TransactionAttribute();
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
