import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { BeneficiaryComponent } from 'app/entities/beneficiary/beneficiary.component';
import { BeneficiaryService } from 'app/entities/beneficiary/beneficiary.service';
import { Beneficiary } from 'app/shared/model/beneficiary.model';

describe('Component Tests', () => {
  describe('Beneficiary Management Component', () => {
    let comp: BeneficiaryComponent;
    let fixture: ComponentFixture<BeneficiaryComponent>;
    let service: BeneficiaryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [BeneficiaryComponent],
      })
        .overrideTemplate(BeneficiaryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeneficiaryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeneficiaryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Beneficiary(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.beneficiaries && comp.beneficiaries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
