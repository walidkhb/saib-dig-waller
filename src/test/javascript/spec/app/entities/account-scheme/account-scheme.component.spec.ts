import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { AccountSchemeComponent } from 'app/entities/account-scheme/account-scheme.component';
import { AccountSchemeService } from 'app/entities/account-scheme/account-scheme.service';
import { AccountScheme } from 'app/shared/model/account-scheme.model';

describe('Component Tests', () => {
  describe('AccountScheme Management Component', () => {
    let comp: AccountSchemeComponent;
    let fixture: ComponentFixture<AccountSchemeComponent>;
    let service: AccountSchemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [AccountSchemeComponent],
      })
        .overrideTemplate(AccountSchemeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountSchemeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountSchemeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AccountScheme(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accountSchemes && comp.accountSchemes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
