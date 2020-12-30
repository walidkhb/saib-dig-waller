import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDestinationChargeAmount, DestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';
import { DestinationChargeAmountService } from './destination-charge-amount.service';
import { DestinationChargeAmountComponent } from './destination-charge-amount.component';
import { DestinationChargeAmountDetailComponent } from './destination-charge-amount-detail.component';
import { DestinationChargeAmountUpdateComponent } from './destination-charge-amount-update.component';

@Injectable({ providedIn: 'root' })
export class DestinationChargeAmountResolve implements Resolve<IDestinationChargeAmount> {
  constructor(private service: DestinationChargeAmountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDestinationChargeAmount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((destinationChargeAmount: HttpResponse<DestinationChargeAmount>) => {
          if (destinationChargeAmount.body) {
            return of(destinationChargeAmount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DestinationChargeAmount());
  }
}

export const destinationChargeAmountRoute: Routes = [
  {
    path: '',
    component: DestinationChargeAmountComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.destinationChargeAmount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DestinationChargeAmountDetailComponent,
    resolve: {
      destinationChargeAmount: DestinationChargeAmountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.destinationChargeAmount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DestinationChargeAmountUpdateComponent,
    resolve: {
      destinationChargeAmount: DestinationChargeAmountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.destinationChargeAmount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DestinationChargeAmountUpdateComponent,
    resolve: {
      destinationChargeAmount: DestinationChargeAmountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.destinationChargeAmount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
