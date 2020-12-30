import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TransactionDetailsComponent } from 'app/entities/transaction-details/transaction-details.component';
import { TransactionDetailsService } from 'app/entities/transaction-details/transaction-details.service';
import { TransactionDetails } from 'app/shared/model/transaction-details.model';

describe('Component Tests', () => {
  describe('TransactionDetails Management Component', () => {
    let comp: TransactionDetailsComponent;
    let fixture: ComponentFixture<TransactionDetailsComponent>;
    let service: TransactionDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TransactionDetailsComponent],
      })
        .overrideTemplate(TransactionDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionDetails && comp.transactionDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
