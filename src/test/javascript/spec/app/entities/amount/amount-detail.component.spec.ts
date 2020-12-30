import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { AmountDetailComponent } from 'app/entities/amount/amount-detail.component';
import { Amount } from 'app/shared/model/amount.model';

describe('Component Tests', () => {
  describe('Amount Management Detail Component', () => {
    let comp: AmountDetailComponent;
    let fixture: ComponentFixture<AmountDetailComponent>;
    const route = ({ data: of({ amount: new Amount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [AmountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AmountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load amount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.amount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
