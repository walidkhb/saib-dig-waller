import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { DistrictListUpdateComponent } from 'app/entities/district-list/district-list-update.component';
import { DistrictListService } from 'app/entities/district-list/district-list.service';
import { DistrictList } from 'app/shared/model/district-list.model';

describe('Component Tests', () => {
  describe('DistrictList Management Update Component', () => {
    let comp: DistrictListUpdateComponent;
    let fixture: ComponentFixture<DistrictListUpdateComponent>;
    let service: DistrictListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [DistrictListUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DistrictListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DistrictListUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DistrictListService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DistrictList(123);
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
        const entity = new DistrictList();
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
