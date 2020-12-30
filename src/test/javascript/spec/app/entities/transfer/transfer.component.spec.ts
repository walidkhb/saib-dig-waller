import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TransferComponent } from 'app/entities/transfer/transfer.component';
import { TransferService } from 'app/entities/transfer/transfer.service';
import { Transfer } from 'app/shared/model/transfer.model';

describe('Component Tests', () => {
  describe('Transfer Management Component', () => {
    let comp: TransferComponent;
    let fixture: ComponentFixture<TransferComponent>;
    let service: TransferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TransferComponent],
      })
        .overrideTemplate(TransferComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransferComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransferService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Transfer(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transfers && comp.transfers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
