import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { VersionListComponent } from 'app/entities/version-list/version-list.component';
import { VersionListService } from 'app/entities/version-list/version-list.service';
import { VersionList } from 'app/shared/model/version-list.model';

describe('Component Tests', () => {
  describe('VersionList Management Component', () => {
    let comp: VersionListComponent;
    let fixture: ComponentFixture<VersionListComponent>;
    let service: VersionListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [VersionListComponent],
      })
        .overrideTemplate(VersionListComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VersionListComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VersionListService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VersionList(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.versionLists && comp.versionLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
