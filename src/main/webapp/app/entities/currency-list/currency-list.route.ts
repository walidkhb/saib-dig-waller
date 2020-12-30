import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICurrencyList, CurrencyList } from 'app/shared/model/currency-list.model';
import { CurrencyListService } from './currency-list.service';
import { CurrencyListComponent } from './currency-list.component';
import { CurrencyListDetailComponent } from './currency-list-detail.component';
import { CurrencyListUpdateComponent } from './currency-list-update.component';

@Injectable({ providedIn: 'root' })
export class CurrencyListResolve implements Resolve<ICurrencyList> {
  constructor(private service: CurrencyListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICurrencyList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((currencyList: HttpResponse<CurrencyList>) => {
          if (currencyList.body) {
            return of(currencyList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CurrencyList());
  }
}

export const currencyListRoute: Routes = [
  {
    path: '',
    component: CurrencyListComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.currencyList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CurrencyListDetailComponent,
    resolve: {
      currencyList: CurrencyListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.currencyList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CurrencyListUpdateComponent,
    resolve: {
      currencyList: CurrencyListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.currencyList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CurrencyListUpdateComponent,
    resolve: {
      currencyList: CurrencyListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.currencyList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
