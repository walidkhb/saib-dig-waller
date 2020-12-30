import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CountryListDetailComponent } from 'app/entities/country-list/country-list-detail.component';
import { CountryList } from 'app/shared/model/country-list.model';

describe('Component Tests', () => {
  describe('CountryList Management Detail Component', () => {
    let comp: CountryListDetailComponent;
    let fixture: ComponentFixture<CountryListDetailComponent>;
    const route = ({ data: of({ countryList: new CountryList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CountryListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CountryListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CountryListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load countryList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.countryList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
