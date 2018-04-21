import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ManageComponent } from './manage.component';
import { ManageDetailComponent } from './manage-detail.component';
import { ManagePopupComponent } from './manage-dialog.component';
import { ManageDeletePopupComponent } from './manage-delete-dialog.component';

@Injectable()
export class ManageResolvePagingParams implements Resolve<any> {

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

export const manageRoute: Routes = [
    {
        path: 'manage',
        component: ManageComponent,
        resolve: {
            'pagingParams': ManageResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.manage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'manage/:id',
        component: ManageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.manage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const managePopupRoute: Routes = [
    {
        path: 'manage-new',
        component: ManagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.manage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'manage/:id/edit',
        component: ManagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.manage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'manage/:id/delete',
        component: ManageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.manage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
