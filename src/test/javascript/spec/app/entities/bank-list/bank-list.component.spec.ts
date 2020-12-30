import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { BankListComponent } from 'app/entities/bank-list/bank-list.component';
import { BankListService } from 'app/entities/bank-list/bank-list.service';
import { BankList } from 'app/shared/model/bank-list.model';

describe('Component Tests', () => {
  describe('BankList Management Component', () => {
    let comp: BankListComponent;
    let fixture: ComponentFixture<BankListComponent>;
    let service: BankListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [BankListComponent],
      })
        .overrideTemplate(BankListComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BankListComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankListService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BankList(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bankLists && comp.bankLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
