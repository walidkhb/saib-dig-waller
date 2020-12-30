import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { DestinationChargeAmountDetailComponent } from 'app/entities/destination-charge-amount/destination-charge-amount-detail.component';
import { DestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';

describe('Component Tests', () => {
  describe('DestinationChargeAmount Management Detail Component', () => {
    let comp: DestinationChargeAmountDetailComponent;
    let fixture: ComponentFixture<DestinationChargeAmountDetailComponent>;
    const route = ({ data: of({ destinationChargeAmount: new DestinationChargeAmount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [DestinationChargeAmountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DestinationChargeAmountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DestinationChargeAmountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load destinationChargeAmount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.destinationChargeAmount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
