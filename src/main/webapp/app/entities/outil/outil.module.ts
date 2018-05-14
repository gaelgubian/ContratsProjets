import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SullyBootSharedModule } from '../../shared';
import {
    OutilService,
    OutilPopupService,
    OutilComponent,
    OutilDetailComponent,
    OutilDialogComponent,
    OutilPopupComponent,
    OutilDeletePopupComponent,
    OutilDeleteDialogComponent,
    outilRoute,
    outilPopupRoute,
} from './';

const ENTITY_STATES = [
    ...outilRoute,
    ...outilPopupRoute,
];

@NgModule({
    imports: [
        SullyBootSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OutilComponent,
        OutilDetailComponent,
        OutilDialogComponent,
        OutilDeleteDialogComponent,
        OutilPopupComponent,
        OutilDeletePopupComponent,
    ],
    entryComponents: [
        OutilComponent,
        OutilDialogComponent,
        OutilPopupComponent,
        OutilDeleteDialogComponent,
        OutilDeletePopupComponent,
    ],
    providers: [
        OutilService,
        OutilPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SullyBootOutilModule {}
