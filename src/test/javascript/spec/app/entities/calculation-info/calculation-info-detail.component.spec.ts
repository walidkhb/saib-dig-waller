import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CalculationInfoDetailComponent } from 'app/entities/calculation-info/calculation-info-detail.component';
import { CalculationInfo } from 'app/shared/model/calculation-info.model';

describe('Component Tests', () => {
  describe('CalculationInfo Management Detail Component', () => {
    let comp: CalculationInfoDetailComponent;
    let fixture: ComponentFixture<CalculationInfoDetailComponent>;
    const route = ({ data: of({ calculationInfo: new CalculationInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CalculationInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CalculationInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CalculationInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load calculationInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.calculationInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
