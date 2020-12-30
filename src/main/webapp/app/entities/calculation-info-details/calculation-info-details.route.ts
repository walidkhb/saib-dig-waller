import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICalculationInfoDetails, CalculationInfoDetails } from 'app/shared/model/calculation-info-details.model';
import { CalculationInfoDetailsService } from './calculation-info-details.service';
import { CalculationInfoDetailsComponent } from './calculation-info-details.component';
import { CalculationInfoDetailsDetailComponent } from './calculation-info-details-detail.component';
import { CalculationInfoDetailsUpdateComponent } from './calculation-info-details-update.component';

@Injectable({ providedIn: 'root' })
export class CalculationInfoDetailsResolve implements Resolve<ICalculationInfoDetails> {
  constructor(private service: CalculationInfoDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICalculationInfoDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((calculationInfoDetails: HttpResponse<CalculationInfoDetails>) => {
          if (calculationInfoDetails.body) {
            return of(calculationInfoDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CalculationInfoDetails());
  }
}

export const calculationInfoDetailsRoute: Routes = [
  {
    path: '',
    component: CalculationInfoDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.calculationInfoDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CalculationInfoDetailsDetailComponent,
    resolve: {
      calculationInfoDetails: CalculationInfoDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.calculationInfoDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CalculationInfoDetailsUpdateComponent,
    resolve: {
      calculationInfoDetails: CalculationInfoDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.calculationInfoDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CalculationInfoDetailsUpdateComponent,
    resolve: {
      calculationInfoDetails: CalculationInfoDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.calculationInfoDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
