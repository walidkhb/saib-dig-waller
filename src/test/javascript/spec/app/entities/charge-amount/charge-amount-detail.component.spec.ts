import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { ChargeAmountDetailComponent } from 'app/entities/charge-amount/charge-amount-detail.component';
import { ChargeAmount } from 'app/shared/model/charge-amount.model';

describe('Component Tests', () => {
  describe('ChargeAmount Management Detail Component', () => {
    let comp: ChargeAmountDetailComponent;
    let fixture: ComponentFixture<ChargeAmountDetailComponent>;
    const route = ({ data: of({ chargeAmount: new ChargeAmount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [ChargeAmountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ChargeAmountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChargeAmountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load chargeAmount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chargeAmount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
