import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPaymentDetails, PaymentDetails } from 'app/shared/model/payment-details.model';
import { PaymentDetailsService } from './payment-details.service';
import { PaymentDetailsComponent } from './payment-details.component';
import { PaymentDetailsDetailComponent } from './payment-details-detail.component';
import { PaymentDetailsUpdateComponent } from './payment-details-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentDetailsResolve implements Resolve<IPaymentDetails> {
  constructor(private service: PaymentDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paymentDetails: HttpResponse<PaymentDetails>) => {
          if (paymentDetails.body) {
            return of(paymentDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentDetails());
  }
}

export const paymentDetailsRoute: Routes = [
  {
    path: '',
    component: PaymentDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.paymentDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentDetailsDetailComponent,
    resolve: {
      paymentDetails: PaymentDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.paymentDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentDetailsUpdateComponent,
    resolve: {
      paymentDetails: PaymentDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.paymentDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentDetailsUpdateComponent,
    resolve: {
      paymentDetails: PaymentDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.paymentDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
