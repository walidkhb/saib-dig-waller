import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { BankListUpdateComponent } from 'app/entities/bank-list/bank-list-update.component';
import { BankListService } from 'app/entities/bank-list/bank-list.service';
import { BankList } from 'app/shared/model/bank-list.model';

describe('Component Tests', () => {
  describe('BankList Management Update Component', () => {
    let comp: BankListUpdateComponent;
    let fixture: ComponentFixture<BankListUpdateComponent>;
    let service: BankListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [BankListUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BankListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BankListUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankListService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BankList(123);
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
        const entity = new BankList();
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
