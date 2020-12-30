import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CreditorUpdateComponent } from 'app/entities/creditor/creditor-update.component';
import { CreditorService } from 'app/entities/creditor/creditor.service';
import { Creditor } from 'app/shared/model/creditor.model';

describe('Component Tests', () => {
  describe('Creditor Management Update Component', () => {
    let comp: CreditorUpdateComponent;
    let fixture: ComponentFixture<CreditorUpdateComponent>;
    let service: CreditorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CreditorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CreditorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CreditorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CreditorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Creditor(123);
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
        const entity = new Creditor();
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
