import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AmountComponent } from './amount.component';
import { AmountDetailComponent } from './amount-detail.component';
import { AmountPopupComponent } from './amount-dialog.component';
import { AmountDeletePopupComponent } from './amount-delete-dialog.component';

@Injectable()
export class AmountResolvePagingParams implements Resolve<any> {

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

export const amountRoute: Routes = [
    {
        path: 'amount',
        component: AmountComponent,
        resolve: {
            'pagingParams': AmountResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.amount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'amount/:id',
        component: AmountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.amount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const amountPopupRoute: Routes = [
    {
        path: 'amount-new',
        component: AmountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.amount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amount/:id/edit',
        component: AmountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.amount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amount/:id/delete',
        component: AmountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.amount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
