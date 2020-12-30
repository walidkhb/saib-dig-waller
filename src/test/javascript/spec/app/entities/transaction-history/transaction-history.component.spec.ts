import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TransactionHistoryComponent } from 'app/entities/transaction-history/transaction-history.component';
import { TransactionHistoryService } from 'app/entities/transaction-history/transaction-history.service';
import { TransactionHistory } from 'app/shared/model/transaction-history.model';

describe('Component Tests', () => {
  describe('TransactionHistory Management Component', () => {
    let comp: TransactionHistoryComponent;
    let fixture: ComponentFixture<TransactionHistoryComponent>;
    let service: TransactionHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TransactionHistoryComponent],
      })
        .overrideTemplate(TransactionHistoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionHistoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionHistoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionHistory(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionHistories && comp.transactionHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
