import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { BankListDetailComponent } from 'app/entities/bank-list/bank-list-detail.component';
import { BankList } from 'app/shared/model/bank-list.model';

describe('Component Tests', () => {
  describe('BankList Management Detail Component', () => {
    let comp: BankListDetailComponent;
    let fixture: ComponentFixture<BankListDetailComponent>;
    const route = ({ data: of({ bankList: new BankList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [BankListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BankListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BankListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bankList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bankList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
