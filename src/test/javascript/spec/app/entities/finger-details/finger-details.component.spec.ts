import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { FingerDetailsComponent } from 'app/entities/finger-details/finger-details.component';
import { FingerDetailsService } from 'app/entities/finger-details/finger-details.service';
import { FingerDetails } from 'app/shared/model/finger-details.model';

describe('Component Tests', () => {
  describe('FingerDetails Management Component', () => {
    let comp: FingerDetailsComponent;
    let fixture: ComponentFixture<FingerDetailsComponent>;
    let service: FingerDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [FingerDetailsComponent],
      })
        .overrideTemplate(FingerDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FingerDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FingerDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FingerDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fingerDetails && comp.fingerDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
