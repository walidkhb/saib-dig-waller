import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransactionDetails, TransactionDetails } from 'app/shared/model/transaction-details.model';
import { TransactionDetailsService } from './transaction-details.service';
import { TransactionDetailsComponent } from './transaction-details.component';
import { TransactionDetailsDetailComponent } from './transaction-details-detail.component';
import { TransactionDetailsUpdateComponent } from './transaction-details-update.component';

@Injectable({ providedIn: 'root' })
export class TransactionDetailsResolve implements Resolve<ITransactionDetails> {
  constructor(private service: TransactionDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransactionDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transactionDetails: HttpResponse<TransactionDetails>) => {
          if (transactionDetails.body) {
            return of(transactionDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransactionDetails());
  }
}

export const transactionDetailsRoute: Routes = [
  {
    path: '',
    component: TransactionDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransactionDetailsDetailComponent,
    resolve: {
      transactionDetails: TransactionDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransactionDetailsUpdateComponent,
    resolve: {
      transactionDetails: TransactionDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransactionDetailsUpdateComponent,
    resolve: {
      transactionDetails: TransactionDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
