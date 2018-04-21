import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CashDeskComponent } from './cash-desk.component';
import { CashDeskDetailComponent } from './cash-desk-detail.component';
import { CashDeskPopupComponent } from './cash-desk-dialog.component';
import { CashDeskDeletePopupComponent } from './cash-desk-delete-dialog.component';

@Injectable()
export class CashDeskResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const cashDeskRoute: Routes = [
    {
        path: 'cash-desk',
        component: CashDeskComponent,
        resolve: {
            'pagingParams': CashDeskResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.cashDesk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cash-desk/:id',
        component: CashDeskDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.cashDesk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cashDeskPopupRoute: Routes = [
    {
        path: 'cash-desk-new',
        component: CashDeskPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.cashDesk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cash-desk/:id/edit',
        component: CashDeskPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.cashDesk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cash-desk/:id/delete',
        component: CashDeskDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.cashDesk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
