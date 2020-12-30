import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { DebtorUpdateComponent } from 'app/entities/debtor/debtor-update.component';
import { DebtorService } from 'app/entities/debtor/debtor.service';
import { Debtor } from 'app/shared/model/debtor.model';

describe('Component Tests', () => {
  describe('Debtor Management Update Component', () => {
    let comp: DebtorUpdateComponent;
    let fixture: ComponentFixture<DebtorUpdateComponent>;
    let service: DebtorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [DebtorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DebtorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DebtorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DebtorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Debtor(123);
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
        const entity = new Debtor();
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
