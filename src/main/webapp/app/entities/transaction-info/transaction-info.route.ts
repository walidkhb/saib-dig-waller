import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransactionInfo, TransactionInfo } from 'app/shared/model/transaction-info.model';
import { TransactionInfoService } from './transaction-info.service';
import { TransactionInfoComponent } from './transaction-info.component';
import { TransactionInfoDetailComponent } from './transaction-info-detail.component';
import { TransactionInfoUpdateComponent } from './transaction-info-update.component';

@Injectable({ providedIn: 'root' })
export class TransactionInfoResolve implements Resolve<ITransactionInfo> {
  constructor(private service: TransactionInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransactionInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transactionInfo: HttpResponse<TransactionInfo>) => {
          if (transactionInfo.body) {
            return of(transactionInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransactionInfo());
  }
}

export const transactionInfoRoute: Routes = [
  {
    path: '',
    component: TransactionInfoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransactionInfoDetailComponent,
    resolve: {
      transactionInfo: TransactionInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransactionInfoUpdateComponent,
    resolve: {
      transactionInfo: TransactionInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransactionInfoUpdateComponent,
    resolve: {
      transactionInfo: TransactionInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
