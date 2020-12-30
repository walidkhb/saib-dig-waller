import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { VersionListDetailComponent } from 'app/entities/version-list/version-list-detail.component';
import { VersionList } from 'app/shared/model/version-list.model';

describe('Component Tests', () => {
  describe('VersionList Management Detail Component', () => {
    let comp: VersionListDetailComponent;
    let fixture: ComponentFixture<VersionListDetailComponent>;
    const route = ({ data: of({ versionList: new VersionList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [VersionListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VersionListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VersionListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load versionList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.versionList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
