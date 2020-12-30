import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDebtor, Debtor } from 'app/shared/model/debtor.model';
import { DebtorService } from './debtor.service';
import { DebtorComponent } from './debtor.component';
import { DebtorDetailComponent } from './debtor-detail.component';
import { DebtorUpdateComponent } from './debtor-update.component';

@Injectable({ providedIn: 'root' })
export class DebtorResolve implements Resolve<IDebtor> {
  constructor(private service: DebtorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDebtor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((debtor: HttpResponse<Debtor>) => {
          if (debtor.body) {
            return of(debtor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Debtor());
  }
}

export const debtorRoute: Routes = [
  {
    path: '',
    component: DebtorComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.debtor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DebtorDetailComponent,
    resolve: {
      debtor: DebtorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.debtor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DebtorUpdateComponent,
    resolve: {
      debtor: DebtorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.debtor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DebtorUpdateComponent,
    resolve: {
      debtor: DebtorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.debtor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
