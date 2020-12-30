import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TransferDetailComponent } from 'app/entities/transfer/transfer-detail.component';
import { Transfer } from 'app/shared/model/transfer.model';

describe('Component Tests', () => {
  describe('Transfer Management Detail Component', () => {
    let comp: TransferDetailComponent;
    let fixture: ComponentFixture<TransferDetailComponent>;
    const route = ({ data: of({ transfer: new Transfer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TransferDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TransferDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransferDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load transfer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transfer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
