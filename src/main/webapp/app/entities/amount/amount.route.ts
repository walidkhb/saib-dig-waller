import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAmount, Amount } from 'app/shared/model/amount.model';
import { AmountService } from './amount.service';
import { AmountComponent } from './amount.component';
import { AmountDetailComponent } from './amount-detail.component';
import { AmountUpdateComponent } from './amount-update.component';

@Injectable({ providedIn: 'root' })
export class AmountResolve implements Resolve<IAmount> {
  constructor(private service: AmountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAmount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((amount: HttpResponse<Amount>) => {
          if (amount.body) {
            return of(amount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Amount());
  }
}

export const amountRoute: Routes = [
  {
    path: '',
    component: AmountComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.amount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AmountDetailComponent,
    resolve: {
      amount: AmountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.amount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AmountUpdateComponent,
    resolve: {
      amount: AmountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.amount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AmountUpdateComponent,
    resolve: {
      amount: AmountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.amount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
