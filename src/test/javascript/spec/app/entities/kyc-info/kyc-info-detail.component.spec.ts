import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KYCInfoDetailComponent } from 'app/entities/kyc-info/kyc-info-detail.component';
import { KYCInfo } from 'app/shared/model/kyc-info.model';

describe('Component Tests', () => {
  describe('KYCInfo Management Detail Component', () => {
    let comp: KYCInfoDetailComponent;
    let fixture: ComponentFixture<KYCInfoDetailComponent>;
    const route = ({ data: of({ kYCInfo: new KYCInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KYCInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KYCInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KYCInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kYCInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kYCInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
