import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { ChargeAmountComponent } from 'app/entities/charge-amount/charge-amount.component';
import { ChargeAmountService } from 'app/entities/charge-amount/charge-amount.service';
import { ChargeAmount } from 'app/shared/model/charge-amount.model';

describe('Component Tests', () => {
  describe('ChargeAmount Management Component', () => {
    let comp: ChargeAmountComponent;
    let fixture: ComponentFixture<ChargeAmountComponent>;
    let service: ChargeAmountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [ChargeAmountComponent],
      })
        .overrideTemplate(ChargeAmountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChargeAmountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChargeAmountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ChargeAmount(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.chargeAmounts && comp.chargeAmounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
