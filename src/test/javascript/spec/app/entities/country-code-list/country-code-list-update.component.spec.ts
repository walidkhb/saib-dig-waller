import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CountryCodeListUpdateComponent } from 'app/entities/country-code-list/country-code-list-update.component';
import { CountryCodeListService } from 'app/entities/country-code-list/country-code-list.service';
import { CountryCodeList } from 'app/shared/model/country-code-list.model';

describe('Component Tests', () => {
  describe('CountryCodeList Management Update Component', () => {
    let comp: CountryCodeListUpdateComponent;
    let fixture: ComponentFixture<CountryCodeListUpdateComponent>;
    let service: CountryCodeListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CountryCodeListUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CountryCodeListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CountryCodeListUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CountryCodeListService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CountryCodeList(123);
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
        const entity = new CountryCodeList();
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
