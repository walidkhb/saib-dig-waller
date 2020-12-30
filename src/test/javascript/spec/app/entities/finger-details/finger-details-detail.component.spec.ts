import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { FingerDetailsDetailComponent } from 'app/entities/finger-details/finger-details-detail.component';
import { FingerDetails } from 'app/shared/model/finger-details.model';

describe('Component Tests', () => {
  describe('FingerDetails Management Detail Component', () => {
    let comp: FingerDetailsDetailComponent;
    let fixture: ComponentFixture<FingerDetailsDetailComponent>;
    const route = ({ data: of({ fingerDetails: new FingerDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [FingerDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FingerDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FingerDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fingerDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fingerDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
