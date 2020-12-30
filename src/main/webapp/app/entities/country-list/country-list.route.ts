import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICountryList, CountryList } from 'app/shared/model/country-list.model';
import { CountryListService } from './country-list.service';
import { CountryListComponent } from './country-list.component';
import { CountryListDetailComponent } from './country-list-detail.component';
import { CountryListUpdateComponent } from './country-list-update.component';

@Injectable({ providedIn: 'root' })
export class CountryListResolve implements Resolve<ICountryList> {
  constructor(private service: CountryListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICountryList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((countryList: HttpResponse<CountryList>) => {
          if (countryList.body) {
            return of(countryList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CountryList());
  }
}

export const countryListRoute: Routes = [
  {
    path: '',
    component: CountryListComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.countryList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CountryListDetailComponent,
    resolve: {
      countryList: CountryListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.countryList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CountryListUpdateComponent,
    resolve: {
      countryList: CountryListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.countryList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CountryListUpdateComponent,
    resolve: {
      countryList: CountryListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.countryList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
