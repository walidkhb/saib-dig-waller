import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDistrictList, DistrictList } from 'app/shared/model/district-list.model';
import { DistrictListService } from './district-list.service';
import { DistrictListComponent } from './district-list.component';
import { DistrictListDetailComponent } from './district-list-detail.component';
import { DistrictListUpdateComponent } from './district-list-update.component';

@Injectable({ providedIn: 'root' })
export class DistrictListResolve implements Resolve<IDistrictList> {
  constructor(private service: DistrictListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDistrictList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((districtList: HttpResponse<DistrictList>) => {
          if (districtList.body) {
            return of(districtList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DistrictList());
  }
}

export const districtListRoute: Routes = [
  {
    path: '',
    component: DistrictListComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.districtList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DistrictListDetailComponent,
    resolve: {
      districtList: DistrictListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.districtList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DistrictListUpdateComponent,
    resolve: {
      districtList: DistrictListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.districtList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DistrictListUpdateComponent,
    resolve: {
      districtList: DistrictListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.districtList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
