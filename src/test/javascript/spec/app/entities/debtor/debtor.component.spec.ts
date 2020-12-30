import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { DebtorComponent } from 'app/entities/debtor/debtor.component';
import { DebtorService } from 'app/entities/debtor/debtor.service';
import { Debtor } from 'app/shared/model/debtor.model';

describe('Component Tests', () => {
  describe('Debtor Management Component', () => {
    let comp: DebtorComponent;
    let fixture: ComponentFixture<DebtorComponent>;
    let service: DebtorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [DebtorComponent],
      })
        .overrideTemplate(DebtorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DebtorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DebtorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Debtor(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.debtors && comp.debtors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
