import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { AccountSchemeUpdateComponent } from 'app/entities/account-scheme/account-scheme-update.component';
import { AccountSchemeService } from 'app/entities/account-scheme/account-scheme.service';
import { AccountScheme } from 'app/shared/model/account-scheme.model';

describe('Component Tests', () => {
  describe('AccountScheme Management Update Component', () => {
    let comp: AccountSchemeUpdateComponent;
    let fixture: ComponentFixture<AccountSchemeUpdateComponent>;
    let service: AccountSchemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [AccountSchemeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AccountSchemeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountSchemeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountSchemeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountScheme(123);
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
        const entity = new AccountScheme();
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
