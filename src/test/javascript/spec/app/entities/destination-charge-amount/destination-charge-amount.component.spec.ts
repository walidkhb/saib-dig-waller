import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { DestinationChargeAmountComponent } from 'app/entities/destination-charge-amount/destination-charge-amount.component';
import { DestinationChargeAmountService } from 'app/entities/destination-charge-amount/destination-charge-amount.service';
import { DestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';

describe('Component Tests', () => {
  describe('DestinationChargeAmount Management Component', () => {
    let comp: DestinationChargeAmountComponent;
    let fixture: ComponentFixture<DestinationChargeAmountComponent>;
    let service: DestinationChargeAmountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [DestinationChargeAmountComponent],
      })
        .overrideTemplate(DestinationChargeAmountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DestinationChargeAmountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DestinationChargeAmountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DestinationChargeAmount(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.destinationChargeAmounts && comp.destinationChargeAmounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
