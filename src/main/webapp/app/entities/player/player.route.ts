import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PlayerComponent } from './player.component';
import { PlayerDetailComponent } from './player-detail.component';
import { PlayerPopupComponent } from './player-dialog.component';
import { PlayerDeletePopupComponent } from './player-delete-dialog.component';

@Injectable()
export class PlayerResolvePagingParams implements Resolve<any> {

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

export const playerRoute: Routes = [
    {
        path: 'player',
        component: PlayerComponent,
        resolve: {
            'pagingParams': PlayerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.player.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'player/:id',
        component: PlayerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.player.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const playerPopupRoute: Routes = [
    {
        path: 'player-new',
        component: PlayerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.player.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'player/:id/edit',
        component: PlayerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.player.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'player/:id/delete',
        component: PlayerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'betsportV2App.player.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
