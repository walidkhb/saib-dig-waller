import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CustomerPreferenceComponent } from 'app/entities/customer-preference/customer-preference.component';
import { CustomerPreferenceService } from 'app/entities/customer-preference/customer-preference.service';
import { CustomerPreference } from 'app/shared/model/customer-preference.model';

describe('Component Tests', () => {
  describe('CustomerPreference Management Component', () => {
    let comp: CustomerPreferenceComponent;
    let fixture: ComponentFixture<CustomerPreferenceComponent>;
    let service: CustomerPreferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CustomerPreferenceComponent],
      })
        .overrideTemplate(CustomerPreferenceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerPreferenceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerPreferenceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomerPreference(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customerPreferences && comp.customerPreferences[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
