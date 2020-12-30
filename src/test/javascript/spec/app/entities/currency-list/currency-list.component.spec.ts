import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CurrencyListComponent } from 'app/entities/currency-list/currency-list.component';
import { CurrencyListService } from 'app/entities/currency-list/currency-list.service';
import { CurrencyList } from 'app/shared/model/currency-list.model';

describe('Component Tests', () => {
  describe('CurrencyList Management Component', () => {
    let comp: CurrencyListComponent;
    let fixture: ComponentFixture<CurrencyListComponent>;
    let service: CurrencyListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CurrencyListComponent],
      })
        .overrideTemplate(CurrencyListComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CurrencyListComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurrencyListService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CurrencyList(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.currencyLists && comp.currencyLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
