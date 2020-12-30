import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TransactionAttributeComponent } from 'app/entities/transaction-attribute/transaction-attribute.component';
import { TransactionAttributeService } from 'app/entities/transaction-attribute/transaction-attribute.service';
import { TransactionAttribute } from 'app/shared/model/transaction-attribute.model';

describe('Component Tests', () => {
  describe('TransactionAttribute Management Component', () => {
    let comp: TransactionAttributeComponent;
    let fixture: ComponentFixture<TransactionAttributeComponent>;
    let service: TransactionAttributeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TransactionAttributeComponent],
      })
        .overrideTemplate(TransactionAttributeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionAttributeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionAttributeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionAttribute(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionAttributes && comp.transactionAttributes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
