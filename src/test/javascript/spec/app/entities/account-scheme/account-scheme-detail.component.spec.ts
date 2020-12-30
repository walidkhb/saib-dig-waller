import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { AccountSchemeDetailComponent } from 'app/entities/account-scheme/account-scheme-detail.component';
import { AccountScheme } from 'app/shared/model/account-scheme.model';

describe('Component Tests', () => {
  describe('AccountScheme Management Detail Component', () => {
    let comp: AccountSchemeDetailComponent;
    let fixture: ComponentFixture<AccountSchemeDetailComponent>;
    const route = ({ data: of({ accountScheme: new AccountScheme(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [AccountSchemeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AccountSchemeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountSchemeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accountScheme on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountScheme).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
