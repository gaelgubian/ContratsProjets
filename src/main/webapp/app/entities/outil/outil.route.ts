import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OutilComponent } from './outil.component';
import { OutilDetailComponent } from './outil-detail.component';
import { OutilPopupComponent } from './outil-dialog.component';
import { OutilDeletePopupComponent } from './outil-delete-dialog.component';

export const outilRoute: Routes = [
    {
        path: 'outil',
        component: OutilComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Outils'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'outil/:id',
        component: OutilDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Outils'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const outilPopupRoute: Routes = [
    {
        path: 'outil-new',
        component: OutilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Outils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'outil/:id/edit',
        component: OutilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Outils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'outil/:id/delete',
        component: OutilDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Outils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
