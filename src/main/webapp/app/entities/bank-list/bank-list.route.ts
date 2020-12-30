import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBankList, BankList } from 'app/shared/model/bank-list.model';
import { BankListService } from './bank-list.service';
import { BankListComponent } from './bank-list.component';
import { BankListDetailComponent } from './bank-list-detail.component';
import { BankListUpdateComponent } from './bank-list-update.component';

@Injectable({ providedIn: 'root' })
export class BankListResolve implements Resolve<IBankList> {
  constructor(private service: BankListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBankList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bankList: HttpResponse<BankList>) => {
          if (bankList.body) {
            return of(bankList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BankList());
  }
}

export const bankListRoute: Routes = [
  {
    path: '',
    component: BankListComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.bankList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BankListDetailComponent,
    resolve: {
      bankList: BankListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.bankList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BankListUpdateComponent,
    resolve: {
      bankList: BankListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.bankList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BankListUpdateComponent,
    resolve: {
      bankList: BankListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.bankList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
