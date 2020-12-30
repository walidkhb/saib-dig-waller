import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CalculationInfoDetailsDetailComponent } from 'app/entities/calculation-info-details/calculation-info-details-detail.component';
import { CalculationInfoDetails } from 'app/shared/model/calculation-info-details.model';

describe('Component Tests', () => {
  describe('CalculationInfoDetails Management Detail Component', () => {
    let comp: CalculationInfoDetailsDetailComponent;
    let fixture: ComponentFixture<CalculationInfoDetailsDetailComponent>;
    const route = ({ data: of({ calculationInfoDetails: new CalculationInfoDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CalculationInfoDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CalculationInfoDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CalculationInfoDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load calculationInfoDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.calculationInfoDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
