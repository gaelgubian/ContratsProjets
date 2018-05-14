import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { NumeroAffaireComponent } from './numero-affaire.component';
import { NumeroAffaireDetailComponent } from './numero-affaire-detail.component';
import { NumeroAffairePopupComponent } from './numero-affaire-dialog.component';
import { NumeroAffaireDeletePopupComponent } from './numero-affaire-delete-dialog.component';

@Injectable()
export class NumeroAffaireResolvePagingParams implements Resolve<any> {

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

export const numeroAffaireRoute: Routes = [
    {
        path: 'numero-affaire',
        component: NumeroAffaireComponent,
        resolve: {
            'pagingParams': NumeroAffaireResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NumeroAffaires'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'numero-affaire/:id',
        component: NumeroAffaireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NumeroAffaires'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const numeroAffairePopupRoute: Routes = [
    {
        path: 'numero-affaire-new',
        component: NumeroAffairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NumeroAffaires'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'numero-affaire/:id/edit',
        component: NumeroAffairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NumeroAffaires'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'numero-affaire/:id/delete',
        component: NumeroAffaireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NumeroAffaires'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
