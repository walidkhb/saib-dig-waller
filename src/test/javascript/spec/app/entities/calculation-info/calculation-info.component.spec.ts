import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CalculationInfoComponent } from 'app/entities/calculation-info/calculation-info.component';
import { CalculationInfoService } from 'app/entities/calculation-info/calculation-info.service';
import { CalculationInfo } from 'app/shared/model/calculation-info.model';

describe('Component Tests', () => {
  describe('CalculationInfo Management Component', () => {
    let comp: CalculationInfoComponent;
    let fixture: ComponentFixture<CalculationInfoComponent>;
    let service: CalculationInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CalculationInfoComponent],
      })
        .overrideTemplate(CalculationInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalculationInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalculationInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CalculationInfo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.calculationInfos && comp.calculationInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
