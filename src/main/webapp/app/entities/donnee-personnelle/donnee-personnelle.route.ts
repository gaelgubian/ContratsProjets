import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DonneePersonnelleComponent } from './donnee-personnelle.component';
import { DonneePersonnelleDetailComponent } from './donnee-personnelle-detail.component';
import { DonneePersonnellePopupComponent } from './donnee-personnelle-dialog.component';
import { DonneePersonnelleDeletePopupComponent } from './donnee-personnelle-delete-dialog.component';

@Injectable()
export class DonneePersonnelleResolvePagingParams implements Resolve<any> {

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

export const donneePersonnelleRoute: Routes = [
    {
        path: 'donnee-personnelle',
        component: DonneePersonnelleComponent,
        resolve: {
            'pagingParams': DonneePersonnelleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DonneePersonnelles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'donnee-personnelle/:id',
        component: DonneePersonnelleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DonneePersonnelles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const donneePersonnellePopupRoute: Routes = [
    {
        path: 'donnee-personnelle-new',
        component: DonneePersonnellePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DonneePersonnelles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'donnee-personnelle/:id/edit',
        component: DonneePersonnellePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DonneePersonnelles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'donnee-personnelle/:id/delete',
        component: DonneePersonnelleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DonneePersonnelles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
