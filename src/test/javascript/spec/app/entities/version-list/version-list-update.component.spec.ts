import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { VersionListUpdateComponent } from 'app/entities/version-list/version-list-update.component';
import { VersionListService } from 'app/entities/version-list/version-list.service';
import { VersionList } from 'app/shared/model/version-list.model';

describe('Component Tests', () => {
  describe('VersionList Management Update Component', () => {
    let comp: VersionListUpdateComponent;
    let fixture: ComponentFixture<VersionListUpdateComponent>;
    let service: VersionListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [VersionListUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VersionListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VersionListUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VersionListService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VersionList(123);
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
        const entity = new VersionList();
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
