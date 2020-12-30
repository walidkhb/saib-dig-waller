import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAccountScheme, AccountScheme } from 'app/shared/model/account-scheme.model';
import { AccountSchemeService } from './account-scheme.service';
import { AccountSchemeComponent } from './account-scheme.component';
import { AccountSchemeDetailComponent } from './account-scheme-detail.component';
import { AccountSchemeUpdateComponent } from './account-scheme-update.component';

@Injectable({ providedIn: 'root' })
export class AccountSchemeResolve implements Resolve<IAccountScheme> {
  constructor(private service: AccountSchemeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountScheme> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((accountScheme: HttpResponse<AccountScheme>) => {
          if (accountScheme.body) {
            return of(accountScheme.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AccountScheme());
  }
}

export const accountSchemeRoute: Routes = [
  {
    path: '',
    component: AccountSchemeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.accountScheme.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccountSchemeDetailComponent,
    resolve: {
      accountScheme: AccountSchemeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.accountScheme.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccountSchemeUpdateComponent,
    resolve: {
      accountScheme: AccountSchemeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.accountScheme.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccountSchemeUpdateComponent,
    resolve: {
      accountScheme: AccountSchemeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.accountScheme.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
