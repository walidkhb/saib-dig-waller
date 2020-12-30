import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CountryListComponent } from 'app/entities/country-list/country-list.component';
import { CountryListService } from 'app/entities/country-list/country-list.service';
import { CountryList } from 'app/shared/model/country-list.model';

describe('Component Tests', () => {
  describe('CountryList Management Component', () => {
    let comp: CountryListComponent;
    let fixture: ComponentFixture<CountryListComponent>;
    let service: CountryListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CountryListComponent],
      })
        .overrideTemplate(CountryListComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CountryListComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CountryListService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CountryList(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.countryLists && comp.countryLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
