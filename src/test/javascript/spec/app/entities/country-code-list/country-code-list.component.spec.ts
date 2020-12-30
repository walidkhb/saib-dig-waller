import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CountryCodeListComponent } from 'app/entities/country-code-list/country-code-list.component';
import { CountryCodeListService } from 'app/entities/country-code-list/country-code-list.service';
import { CountryCodeList } from 'app/shared/model/country-code-list.model';

describe('Component Tests', () => {
  describe('CountryCodeList Management Component', () => {
    let comp: CountryCodeListComponent;
    let fixture: ComponentFixture<CountryCodeListComponent>;
    let service: CountryCodeListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CountryCodeListComponent],
      })
        .overrideTemplate(CountryCodeListComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CountryCodeListComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CountryCodeListService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CountryCodeList(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.countryCodeLists && comp.countryCodeLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
