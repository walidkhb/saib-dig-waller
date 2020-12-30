import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { BeneficiaryBankDetailComponent } from 'app/entities/beneficiary-bank/beneficiary-bank-detail.component';
import { BeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';

describe('Component Tests', () => {
  describe('BeneficiaryBank Management Detail Component', () => {
    let comp: BeneficiaryBankDetailComponent;
    let fixture: ComponentFixture<BeneficiaryBankDetailComponent>;
    const route = ({ data: of({ beneficiaryBank: new BeneficiaryBank(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [BeneficiaryBankDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BeneficiaryBankDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BeneficiaryBankDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load beneficiaryBank on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.beneficiaryBank).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
