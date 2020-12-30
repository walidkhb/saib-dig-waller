import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBranchList, BranchList } from 'app/shared/model/branch-list.model';
import { BranchListService } from './branch-list.service';
import { BranchListComponent } from './branch-list.component';
import { BranchListDetailComponent } from './branch-list-detail.component';
import { BranchListUpdateComponent } from './branch-list-update.component';

@Injectable({ providedIn: 'root' })
export class BranchListResolve implements Resolve<IBranchList> {
  constructor(private service: BranchListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBranchList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((branchList: HttpResponse<BranchList>) => {
          if (branchList.body) {
            return of(branchList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BranchList());
  }
}

export const branchListRoute: Routes = [
  {
    path: '',
    component: BranchListComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.branchList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BranchListDetailComponent,
    resolve: {
      branchList: BranchListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.branchList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BranchListUpdateComponent,
    resolve: {
      branchList: BranchListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.branchList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BranchListUpdateComponent,
    resolve: {
      branchList: BranchListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.branchList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
