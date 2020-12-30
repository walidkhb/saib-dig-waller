import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFingerDetails, FingerDetails } from 'app/shared/model/finger-details.model';
import { FingerDetailsService } from './finger-details.service';
import { FingerDetailsComponent } from './finger-details.component';
import { FingerDetailsDetailComponent } from './finger-details-detail.component';
import { FingerDetailsUpdateComponent } from './finger-details-update.component';

@Injectable({ providedIn: 'root' })
export class FingerDetailsResolve implements Resolve<IFingerDetails> {
  constructor(private service: FingerDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFingerDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fingerDetails: HttpResponse<FingerDetails>) => {
          if (fingerDetails.body) {
            return of(fingerDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FingerDetails());
  }
}

export const fingerDetailsRoute: Routes = [
  {
    path: '',
    component: FingerDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.fingerDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FingerDetailsDetailComponent,
    resolve: {
      fingerDetails: FingerDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.fingerDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FingerDetailsUpdateComponent,
    resolve: {
      fingerDetails: FingerDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.fingerDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FingerDetailsUpdateComponent,
    resolve: {
      fingerDetails: FingerDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.fingerDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
