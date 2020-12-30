import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TransactionDetailsDetailComponent } from 'app/entities/transaction-details/transaction-details-detail.component';
import { TransactionDetails } from 'app/shared/model/transaction-details.model';

describe('Component Tests', () => {
  describe('TransactionDetails Management Detail Component', () => {
    let comp: TransactionDetailsDetailComponent;
    let fixture: ComponentFixture<TransactionDetailsDetailComponent>;
    const route = ({ data: of({ transactionDetails: new TransactionDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TransactionDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TransactionDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load transactionDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
