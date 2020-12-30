import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBeneficiaryBank, BeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';
import { BeneficiaryBankService } from './beneficiary-bank.service';
import { BeneficiaryBankComponent } from './beneficiary-bank.component';
import { BeneficiaryBankDetailComponent } from './beneficiary-bank-detail.component';
import { BeneficiaryBankUpdateComponent } from './beneficiary-bank-update.component';

@Injectable({ providedIn: 'root' })
export class BeneficiaryBankResolve implements Resolve<IBeneficiaryBank> {
  constructor(private service: BeneficiaryBankService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBeneficiaryBank> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((beneficiaryBank: HttpResponse<BeneficiaryBank>) => {
          if (beneficiaryBank.body) {
            return of(beneficiaryBank.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BeneficiaryBank());
  }
}

export const beneficiaryBankRoute: Routes = [
  {
    path: '',
    component: BeneficiaryBankComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.beneficiaryBank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BeneficiaryBankDetailComponent,
    resolve: {
      beneficiaryBank: BeneficiaryBankResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.beneficiaryBank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BeneficiaryBankUpdateComponent,
    resolve: {
      beneficiaryBank: BeneficiaryBankResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.beneficiaryBank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BeneficiaryBankUpdateComponent,
    resolve: {
      beneficiaryBank: BeneficiaryBankResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.beneficiaryBank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
