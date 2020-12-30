import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { BranchListDetailComponent } from 'app/entities/branch-list/branch-list-detail.component';
import { BranchList } from 'app/shared/model/branch-list.model';

describe('Component Tests', () => {
  describe('BranchList Management Detail Component', () => {
    let comp: BranchListDetailComponent;
    let fixture: ComponentFixture<BranchListDetailComponent>;
    const route = ({ data: of({ branchList: new BranchList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [BranchListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BranchListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BranchListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load branchList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.branchList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
