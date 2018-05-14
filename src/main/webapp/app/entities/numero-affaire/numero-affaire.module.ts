import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SullyBootSharedModule } from '../../shared';
import {
    NumeroAffaireService,
    NumeroAffairePopupService,
    NumeroAffaireComponent,
    NumeroAffaireDetailComponent,
    NumeroAffaireDialogComponent,
    NumeroAffairePopupComponent,
    NumeroAffaireDeletePopupComponent,
    NumeroAffaireDeleteDialogComponent,
    numeroAffaireRoute,
    numeroAffairePopupRoute,
    NumeroAffaireResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...numeroAffaireRoute,
    ...numeroAffairePopupRoute,
];

@NgModule({
    imports: [
        SullyBootSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NumeroAffaireComponent,
        NumeroAffaireDetailComponent,
        NumeroAffaireDialogComponent,
        NumeroAffaireDeleteDialogComponent,
        NumeroAffairePopupComponent,
        NumeroAffaireDeletePopupComponent,
    ],
    entryComponents: [
        NumeroAffaireComponent,
        NumeroAffaireDialogComponent,
        NumeroAffairePopupComponent,
        NumeroAffaireDeleteDialogComponent,
        NumeroAffaireDeletePopupComponent,
    ],
    providers: [
        NumeroAffaireService,
        NumeroAffairePopupService,
        NumeroAffaireResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SullyBootNumeroAffaireModule {}
