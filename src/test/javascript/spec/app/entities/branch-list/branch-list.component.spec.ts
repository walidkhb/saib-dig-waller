import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { BranchListComponent } from 'app/entities/branch-list/branch-list.component';
import { BranchListService } from 'app/entities/branch-list/branch-list.service';
import { BranchList } from 'app/shared/model/branch-list.model';

describe('Component Tests', () => {
  describe('BranchList Management Component', () => {
    let comp: BranchListComponent;
    let fixture: ComponentFixture<BranchListComponent>;
    let service: BranchListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [BranchListComponent],
      })
        .overrideTemplate(BranchListComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BranchListComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BranchListService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BranchList(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.branchLists && comp.branchLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
