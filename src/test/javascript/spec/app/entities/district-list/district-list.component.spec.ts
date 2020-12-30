import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { DistrictListComponent } from 'app/entities/district-list/district-list.component';
import { DistrictListService } from 'app/entities/district-list/district-list.service';
import { DistrictList } from 'app/shared/model/district-list.model';

describe('Component Tests', () => {
  describe('DistrictList Management Component', () => {
    let comp: DistrictListComponent;
    let fixture: ComponentFixture<DistrictListComponent>;
    let service: DistrictListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [DistrictListComponent],
      })
        .overrideTemplate(DistrictListComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DistrictListComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DistrictListService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DistrictList(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.districtLists && comp.districtLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
