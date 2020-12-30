import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { CreditorDetailComponent } from 'app/entities/creditor/creditor-detail.component';
import { Creditor } from 'app/shared/model/creditor.model';

describe('Component Tests', () => {
  describe('Creditor Management Detail Component', () => {
    let comp: CreditorDetailComponent;
    let fixture: ComponentFixture<CreditorDetailComponent>;
    const route = ({ data: of({ creditor: new Creditor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [CreditorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CreditorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CreditorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load creditor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.creditor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
