import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CreditorComponent } from 'app/entities/creditor/creditor.component';
import { CreditorService } from 'app/entities/creditor/creditor.service';
import { Creditor } from 'app/shared/model/creditor.model';

describe('Component Tests', () => {
  describe('Creditor Management Component', () => {
    let comp: CreditorComponent;
    let fixture: ComponentFixture<CreditorComponent>;
    let service: CreditorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CreditorComponent],
      })
        .overrideTemplate(CreditorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CreditorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CreditorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Creditor(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.creditors && comp.creditors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
