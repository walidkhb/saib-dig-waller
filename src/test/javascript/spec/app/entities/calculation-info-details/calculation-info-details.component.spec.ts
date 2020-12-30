import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CalculationInfoDetailsComponent } from 'app/entities/calculation-info-details/calculation-info-details.component';
import { CalculationInfoDetailsService } from 'app/entities/calculation-info-details/calculation-info-details.service';
import { CalculationInfoDetails } from 'app/shared/model/calculation-info-details.model';

describe('Component Tests', () => {
  describe('CalculationInfoDetails Management Component', () => {
    let comp: CalculationInfoDetailsComponent;
    let fixture: ComponentFixture<CalculationInfoDetailsComponent>;
    let service: CalculationInfoDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CalculationInfoDetailsComponent],
      })
        .overrideTemplate(CalculationInfoDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalculationInfoDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalculationInfoDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CalculationInfoDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.calculationInfoDetails && comp.calculationInfoDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
