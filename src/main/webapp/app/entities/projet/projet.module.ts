import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SullyBootSharedModule } from '../../shared';
import {
    ProjetService,
    ProjetPopupService,
    ProjetComponent,
    ProjetDetailComponent,
    ProjetDialogComponent,
    ProjetPopupComponent,
    ProjetDeletePopupComponent,
    ProjetDeleteDialogComponent,
    projetRoute,
    projetPopupRoute,
    ProjetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...projetRoute,
    ...projetPopupRoute,
];

@NgModule({
    imports: [
        SullyBootSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProjetComponent,
        ProjetDetailComponent,
        ProjetDialogComponent,
        ProjetDeleteDialogComponent,
        ProjetPopupComponent,
        ProjetDeletePopupComponent,
    ],
    entryComponents: [
        ProjetComponent,
        ProjetDialogComponent,
        ProjetPopupComponent,
        ProjetDeleteDialogComponent,
        ProjetDeletePopupComponent,
    ],
    providers: [
        ProjetService,
        ProjetPopupService,
        ProjetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SullyBootProjetModule {}
