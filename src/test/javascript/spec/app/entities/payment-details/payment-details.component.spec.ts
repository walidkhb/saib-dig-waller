import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { PaymentDetailsComponent } from 'app/entities/payment-details/payment-details.component';
import { PaymentDetailsService } from 'app/entities/payment-details/payment-details.service';
import { PaymentDetails } from 'app/shared/model/payment-details.model';

describe('Component Tests', () => {
  describe('PaymentDetails Management Component', () => {
    let comp: PaymentDetailsComponent;
    let fixture: ComponentFixture<PaymentDetailsComponent>;
    let service: PaymentDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [PaymentDetailsComponent],
      })
        .overrideTemplate(PaymentDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentDetails && comp.paymentDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
