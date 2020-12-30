import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICalculationInfo, CalculationInfo } from 'app/shared/model/calculation-info.model';
import { CalculationInfoService } from './calculation-info.service';
import { CalculationInfoComponent } from './calculation-info.component';
import { CalculationInfoDetailComponent } from './calculation-info-detail.component';
import { CalculationInfoUpdateComponent } from './calculation-info-update.component';

@Injectable({ providedIn: 'root' })
export class CalculationInfoResolve implements Resolve<ICalculationInfo> {
  constructor(private service: CalculationInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICalculationInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((calculationInfo: HttpResponse<CalculationInfo>) => {
          if (calculationInfo.body) {
            return of(calculationInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CalculationInfo());
  }
}

export const calculationInfoRoute: Routes = [
  {
    path: '',
    component: CalculationInfoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.calculationInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CalculationInfoDetailComponent,
    resolve: {
      calculationInfo: CalculationInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.calculationInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CalculationInfoUpdateComponent,
    resolve: {
      calculationInfo: CalculationInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.calculationInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CalculationInfoUpdateComponent,
    resolve: {
      calculationInfo: CalculationInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.calculationInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
