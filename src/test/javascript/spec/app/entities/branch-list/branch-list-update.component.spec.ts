import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { BranchListUpdateComponent } from 'app/entities/branch-list/branch-list-update.component';
import { BranchListService } from 'app/entities/branch-list/branch-list.service';
import { BranchList } from 'app/shared/model/branch-list.model';

describe('Component Tests', () => {
  describe('BranchList Management Update Component', () => {
    let comp: BranchListUpdateComponent;
    let fixture: ComponentFixture<BranchListUpdateComponent>;
    let service: BranchListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [BranchListUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BranchListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BranchListUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BranchListService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BranchList(123);
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
        const entity = new BranchList();
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
