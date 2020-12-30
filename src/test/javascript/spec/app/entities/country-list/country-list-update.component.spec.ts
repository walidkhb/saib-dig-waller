import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CountryListUpdateComponent } from 'app/entities/country-list/country-list-update.component';
import { CountryListService } from 'app/entities/country-list/country-list.service';
import { CountryList } from 'app/shared/model/country-list.model';

describe('Component Tests', () => {
  describe('CountryList Management Update Component', () => {
    let comp: CountryListUpdateComponent;
    let fixture: ComponentFixture<CountryListUpdateComponent>;
    let service: CountryListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CountryListUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CountryListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CountryListUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CountryListService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CountryList(123);
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
        const entity = new CountryList();
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
