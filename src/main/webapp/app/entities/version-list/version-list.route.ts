import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVersionList, VersionList } from 'app/shared/model/version-list.model';
import { VersionListService } from './version-list.service';
import { VersionListComponent } from './version-list.component';
import { VersionListDetailComponent } from './version-list-detail.component';
import { VersionListUpdateComponent } from './version-list-update.component';

@Injectable({ providedIn: 'root' })
export class VersionListResolve implements Resolve<IVersionList> {
  constructor(private service: VersionListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVersionList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((versionList: HttpResponse<VersionList>) => {
          if (versionList.body) {
            return of(versionList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VersionList());
  }
}

export const versionListRoute: Routes = [
  {
    path: '',
    component: VersionListComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.versionList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VersionListDetailComponent,
    resolve: {
      versionList: VersionListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.versionList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VersionListUpdateComponent,
    resolve: {
      versionList: VersionListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.versionList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VersionListUpdateComponent,
    resolve: {
      versionList: VersionListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.versionList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
