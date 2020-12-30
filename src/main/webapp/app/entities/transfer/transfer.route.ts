import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransfer, Transfer } from 'app/shared/model/transfer.model';
import { TransferService } from './transfer.service';
import { TransferComponent } from './transfer.component';
import { TransferDetailComponent } from './transfer-detail.component';
import { TransferUpdateComponent } from './transfer-update.component';

@Injectable({ providedIn: 'root' })
export class TransferResolve implements Resolve<ITransfer> {
  constructor(private service: TransferService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransfer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transfer: HttpResponse<Transfer>) => {
          if (transfer.body) {
            return of(transfer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Transfer());
  }
}

export const transferRoute: Routes = [
  {
    path: '',
    component: TransferComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transfer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransferDetailComponent,
    resolve: {
      transfer: TransferResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transfer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransferUpdateComponent,
    resolve: {
      transfer: TransferResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transfer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransferUpdateComponent,
    resolve: {
      transfer: TransferResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.transfer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
