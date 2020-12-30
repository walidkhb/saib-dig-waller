import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CountryCodeListDetailComponent } from 'app/entities/country-code-list/country-code-list-detail.component';
import { CountryCodeList } from 'app/shared/model/country-code-list.model';

describe('Component Tests', () => {
  describe('CountryCodeList Management Detail Component', () => {
    let comp: CountryCodeListDetailComponent;
    let fixture: ComponentFixture<CountryCodeListDetailComponent>;
    const route = ({ data: of({ countryCodeList: new CountryCodeList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CountryCodeListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CountryCodeListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CountryCodeListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load countryCodeList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.countryCodeList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
