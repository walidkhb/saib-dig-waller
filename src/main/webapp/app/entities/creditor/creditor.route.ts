import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICreditor, Creditor } from 'app/shared/model/creditor.model';
import { CreditorService } from './creditor.service';
import { CreditorComponent } from './creditor.component';
import { CreditorDetailComponent } from './creditor-detail.component';
import { CreditorUpdateComponent } from './creditor-update.component';

@Injectable({ providedIn: 'root' })
export class CreditorResolve implements Resolve<ICreditor> {
  constructor(private service: CreditorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICreditor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((creditor: HttpResponse<Creditor>) => {
          if (creditor.body) {
            return of(creditor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Creditor());
  }
}

export const creditorRoute: Routes = [
  {
    path: '',
    component: CreditorComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.creditor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CreditorDetailComponent,
    resolve: {
      creditor: CreditorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.creditor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CreditorUpdateComponent,
    resolve: {
      creditor: CreditorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.creditor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CreditorUpdateComponent,
    resolve: {
      creditor: CreditorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.creditor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
