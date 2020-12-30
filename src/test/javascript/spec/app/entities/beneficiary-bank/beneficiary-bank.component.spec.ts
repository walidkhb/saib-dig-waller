import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { BeneficiaryBankComponent } from 'app/entities/beneficiary-bank/beneficiary-bank.component';
import { BeneficiaryBankService } from 'app/entities/beneficiary-bank/beneficiary-bank.service';
import { BeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';

describe('Component Tests', () => {
  describe('BeneficiaryBank Management Component', () => {
    let comp: BeneficiaryBankComponent;
    let fixture: ComponentFixture<BeneficiaryBankComponent>;
    let service: BeneficiaryBankService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [BeneficiaryBankComponent],
      })
        .overrideTemplate(BeneficiaryBankComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeneficiaryBankComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeneficiaryBankService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BeneficiaryBank(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.beneficiaryBanks && comp.beneficiaryBanks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
