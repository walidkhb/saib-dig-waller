import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransactionAttribute, TransactionAttribute } from 'app/shared/model/transaction-attribute.model';
import { TransactionAttributeService } from './transaction-attribute.service';
import { TransactionAttributeComponent } from './transaction-attribute.component';
import { TransactionAttributeDetailComponent } from './transaction-attribute-detail.component';
import { TransactionAttributeUpdateComponent } from './transaction-attribute-update.component';

@Injectable({ providedIn: 'root' })
export class TransactionAttributeResolve implements Resolve<ITransactionAttribute> {
  constructor(private service: TransactionAttributeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransactionAttribute> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transactionAttribute: HttpResponse<TransactionAttribute>) => {
          if (transactionAttribute.body) {
            return of(transactionAttribute.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransactionAttribute());
  }
}

export const transactionAttributeRoute: Routes = [
  {
    path: '',
    component: TransactionAttributeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionAttribute.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransactionAttributeDetailComponent,
    resolve: {
      transactionAttribute: TransactionAttributeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionAttribute.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransactionAttributeUpdateComponent,
    resolve: {
      transactionAttribute: TransactionAttributeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionAttribute.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransactionAttributeUpdateComponent,
    resolve: {
      transactionAttribute: TransactionAttributeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transactionAttribute.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
