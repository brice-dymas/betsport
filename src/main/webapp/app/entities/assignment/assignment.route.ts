import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AssignmentComponent } from './assignment.component';
import { AssignmentDetailComponent } from './assignment-detail.component';
import { AssignmentPopupComponent } from './assignment-dialog.component';
import { AssignmentDeletePopupComponent } from './assignment-delete-dialog.component';

@Injectable()
export class AssignmentResolvePagingParams implements Resolve<any> {

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

export const assignmentRoute: Routes = [
    {
        path: 'assignment',
        component: AssignmentComponent,
        resolve: {
            'pagingParams': AssignmentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.assignment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'assignment/:id',
        component: AssignmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.assignment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const assignmentPopupRoute: Routes = [
    {
        path: 'assignment-new',
        component: AssignmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.assignment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'assignment/:id/edit',
        component: AssignmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.assignment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'assignment/:id/delete',
        component: AssignmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.assignment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
