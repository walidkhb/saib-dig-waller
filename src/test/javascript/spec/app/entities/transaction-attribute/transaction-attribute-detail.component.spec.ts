import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TransactionAttributeDetailComponent } from 'app/entities/transaction-attribute/transaction-attribute-detail.component';
import { TransactionAttribute } from 'app/shared/model/transaction-attribute.model';

describe('Component Tests', () => {
  describe('TransactionAttribute Management Detail Component', () => {
    let comp: TransactionAttributeDetailComponent;
    let fixture: ComponentFixture<TransactionAttributeDetailComponent>;
    const route = ({ data: of({ transactionAttribute: new TransactionAttribute(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TransactionAttributeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TransactionAttributeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionAttributeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load transactionAttribute on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionAttribute).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
