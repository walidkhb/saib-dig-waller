import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CustomerPreferenceDetailComponent } from 'app/entities/customer-preference/customer-preference-detail.component';
import { CustomerPreference } from 'app/shared/model/customer-preference.model';

describe('Component Tests', () => {
  describe('CustomerPreference Management Detail Component', () => {
    let comp: CustomerPreferenceDetailComponent;
    let fixture: ComponentFixture<CustomerPreferenceDetailComponent>;
    const route = ({ data: of({ customerPreference: new CustomerPreference(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CustomerPreferenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomerPreferenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerPreferenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customerPreference on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerPreference).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
