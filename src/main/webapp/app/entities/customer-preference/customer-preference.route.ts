import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomerPreference, CustomerPreference } from 'app/shared/model/customer-preference.model';
import { CustomerPreferenceService } from './customer-preference.service';
import { CustomerPreferenceComponent } from './customer-preference.component';
import { CustomerPreferenceDetailComponent } from './customer-preference-detail.component';
import { CustomerPreferenceUpdateComponent } from './customer-preference-update.component';

@Injectable({ providedIn: 'root' })
export class CustomerPreferenceResolve implements Resolve<ICustomerPreference> {
  constructor(private service: CustomerPreferenceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerPreference> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customerPreference: HttpResponse<CustomerPreference>) => {
          if (customerPreference.body) {
            return of(customerPreference.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerPreference());
  }
}

export const customerPreferenceRoute: Routes = [
  {
    path: '',
    component: CustomerPreferenceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.customerPreference.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerPreferenceDetailComponent,
    resolve: {
      customerPreference: CustomerPreferenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.customerPreference.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerPreferenceUpdateComponent,
    resolve: {
      customerPreference: CustomerPreferenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.customerPreference.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerPreferenceUpdateComponent,
    resolve: {
      customerPreference: CustomerPreferenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.customerPreference.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
