import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KYCInfoComponent } from 'app/entities/kyc-info/kyc-info.component';
import { KYCInfoService } from 'app/entities/kyc-info/kyc-info.service';
import { KYCInfo } from 'app/shared/model/kyc-info.model';

describe('Component Tests', () => {
  describe('KYCInfo Management Component', () => {
    let comp: KYCInfoComponent;
    let fixture: ComponentFixture<KYCInfoComponent>;
    let service: KYCInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KYCInfoComponent],
      })
        .overrideTemplate(KYCInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KYCInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KYCInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new KYCInfo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.kYCInfos && comp.kYCInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
