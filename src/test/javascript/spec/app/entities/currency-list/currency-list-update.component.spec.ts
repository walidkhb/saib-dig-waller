import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CurrencyListUpdateComponent } from 'app/entities/currency-list/currency-list-update.component';
import { CurrencyListService } from 'app/entities/currency-list/currency-list.service';
import { CurrencyList } from 'app/shared/model/currency-list.model';

describe('Component Tests', () => {
  describe('CurrencyList Management Update Component', () => {
    let comp: CurrencyListUpdateComponent;
    let fixture: ComponentFixture<CurrencyListUpdateComponent>;
    let service: CurrencyListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CurrencyListUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CurrencyListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CurrencyListUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurrencyListService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CurrencyList(123);
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
        const entity = new CurrencyList();
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
