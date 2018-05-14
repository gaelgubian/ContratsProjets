import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TraitementComponent } from './traitement.component';
import { TraitementDetailComponent } from './traitement-detail.component';
import { TraitementPopupComponent } from './traitement-dialog.component';
import { TraitementDeletePopupComponent } from './traitement-delete-dialog.component';

@Injectable()
export class TraitementResolvePagingParams implements Resolve<any> {

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

export const traitementRoute: Routes = [
    {
        path: 'traitement',
        component: TraitementComponent,
        resolve: {
            'pagingParams': TraitementResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Traitements'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'traitement/:id',
        component: TraitementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Traitements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const traitementPopupRoute: Routes = [
    {
        path: 'traitement-new',
        component: TraitementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Traitements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'traitement/:id/edit',
        component: TraitementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Traitements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'traitement/:id/delete',
        component: TraitementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Traitements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
