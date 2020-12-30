import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { AmountComponent } from 'app/entities/amount/amount.component';
import { AmountService } from 'app/entities/amount/amount.service';
import { Amount } from 'app/shared/model/amount.model';

describe('Component Tests', () => {
  describe('Amount Management Component', () => {
    let comp: AmountComponent;
    let fixture: ComponentFixture<AmountComponent>;
    let service: AmountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [AmountComponent],
      })
        .overrideTemplate(AmountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Amount(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.amounts && comp.amounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
