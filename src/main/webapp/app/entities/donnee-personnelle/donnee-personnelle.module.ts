import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SullyBootSharedModule } from '../../shared';
import {
    DonneePersonnelleService,
    DonneePersonnellePopupService,
    DonneePersonnelleComponent,
    DonneePersonnelleDetailComponent,
    DonneePersonnelleDialogComponent,
    DonneePersonnellePopupComponent,
    DonneePersonnelleDeletePopupComponent,
    DonneePersonnelleDeleteDialogComponent,
    donneePersonnelleRoute,
    donneePersonnellePopupRoute,
    DonneePersonnelleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...donneePersonnelleRoute,
    ...donneePersonnellePopupRoute,
];

@NgModule({
    imports: [
        SullyBootSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DonneePersonnelleComponent,
        DonneePersonnelleDetailComponent,
        DonneePersonnelleDialogComponent,
        DonneePersonnelleDeleteDialogComponent,
        DonneePersonnellePopupComponent,
        DonneePersonnelleDeletePopupComponent,
    ],
    entryComponents: [
        DonneePersonnelleComponent,
        DonneePersonnelleDialogComponent,
        DonneePersonnellePopupComponent,
        DonneePersonnelleDeleteDialogComponent,
        DonneePersonnelleDeletePopupComponent,
    ],
    providers: [
        DonneePersonnelleService,
        DonneePersonnellePopupService,
        DonneePersonnelleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SullyBootDonneePersonnelleModule {}
