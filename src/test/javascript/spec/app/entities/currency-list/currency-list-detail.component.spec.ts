import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CurrencyListDetailComponent } from 'app/entities/currency-list/currency-list-detail.component';
import { CurrencyList } from 'app/shared/model/currency-list.model';

describe('Component Tests', () => {
  describe('CurrencyList Management Detail Component', () => {
    let comp: CurrencyListDetailComponent;
    let fixture: ComponentFixture<CurrencyListDetailComponent>;
    const route = ({ data: of({ currencyList: new CurrencyList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CurrencyListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CurrencyListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CurrencyListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load currencyList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.currencyList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
