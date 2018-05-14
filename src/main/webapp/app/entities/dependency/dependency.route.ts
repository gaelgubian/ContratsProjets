import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DependencyComponent } from './dependency.component';
import { DependencyDetailComponent } from './dependency-detail.component';
import { DependencyPopupComponent } from './dependency-dialog.component';
import { DependencyDeletePopupComponent } from './dependency-delete-dialog.component';

@Injectable()
export class DependencyResolvePagingParams implements Resolve<any> {

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

export const dependencyRoute: Routes = [
    {
        path: 'dependency',
        component: DependencyComponent,
        resolve: {
            'pagingParams': DependencyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dependencies'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dependency/:id',
        component: DependencyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dependencies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dependencyPopupRoute: Routes = [
    {
        path: 'dependency-new',
        component: DependencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dependencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dependency/:id/edit',
        component: DependencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dependencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dependency/:id/delete',
        component: DependencyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dependencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
