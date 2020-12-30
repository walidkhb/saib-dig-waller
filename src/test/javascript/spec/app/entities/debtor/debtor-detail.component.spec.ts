import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { DebtorDetailComponent } from 'app/entities/debtor/debtor-detail.component';
import { Debtor } from 'app/shared/model/debtor.model';

describe('Component Tests', () => {
  describe('Debtor Management Detail Component', () => {
    let comp: DebtorDetailComponent;
    let fixture: ComponentFixture<DebtorDetailComponent>;
    const route = ({ data: of({ debtor: new Debtor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [DebtorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DebtorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DebtorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load debtor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.debtor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
