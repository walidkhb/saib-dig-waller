import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IChargeAmount, ChargeAmount } from 'app/shared/model/charge-amount.model';
import { ChargeAmountService } from './charge-amount.service';
import { ChargeAmountComponent } from './charge-amount.component';
import { ChargeAmountDetailComponent } from './charge-amount-detail.component';
import { ChargeAmountUpdateComponent } from './charge-amount-update.component';

@Injectable({ providedIn: 'root' })
export class ChargeAmountResolve implements Resolve<IChargeAmount> {
  constructor(private service: ChargeAmountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChargeAmount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((chargeAmount: HttpResponse<ChargeAmount>) => {
          if (chargeAmount.body) {
            return of(chargeAmount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ChargeAmount());
  }
}

export const chargeAmountRoute: Routes = [
  {
    path: '',
    component: ChargeAmountComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.chargeAmount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChargeAmountDetailComponent,
    resolve: {
      chargeAmount: ChargeAmountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.chargeAmount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChargeAmountUpdateComponent,
    resolve: {
      chargeAmount: ChargeAmountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.chargeAmount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChargeAmountUpdateComponent,
    resolve: {
      chargeAmount: ChargeAmountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.chargeAmount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
