import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { DistrictListDetailComponent } from 'app/entities/district-list/district-list-detail.component';
import { DistrictList } from 'app/shared/model/district-list.model';

describe('Component Tests', () => {
  describe('DistrictList Management Detail Component', () => {
    let comp: DistrictListDetailComponent;
    let fixture: ComponentFixture<DistrictListDetailComponent>;
    const route = ({ data: of({ districtList: new DistrictList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [DistrictListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DistrictListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DistrictListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load districtList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.districtList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
