import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICountryCodeList, CountryCodeList } from 'app/shared/model/country-code-list.model';
import { CountryCodeListService } from './country-code-list.service';
import { CountryCodeListComponent } from './country-code-list.component';
import { CountryCodeListDetailComponent } from './country-code-list-detail.component';
import { CountryCodeListUpdateComponent } from './country-code-list-update.component';

@Injectable({ providedIn: 'root' })
export class CountryCodeListResolve implements Resolve<ICountryCodeList> {
  constructor(private service: CountryCodeListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICountryCodeList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((countryCodeList: HttpResponse<CountryCodeList>) => {
          if (countryCodeList.body) {
            return of(countryCodeList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CountryCodeList());
  }
}

export const countryCodeListRoute: Routes = [
  {
    path: '',
    component: CountryCodeListComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.countryCodeList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CountryCodeListDetailComponent,
    resolve: {
      countryCodeList: CountryCodeListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.countryCodeList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CountryCodeListUpdateComponent,
    resolve: {
      countryCodeList: CountryCodeListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.countryCodeList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CountryCodeListUpdateComponent,
    resolve: {
      countryCodeList: CountryCodeListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.countryCodeList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
