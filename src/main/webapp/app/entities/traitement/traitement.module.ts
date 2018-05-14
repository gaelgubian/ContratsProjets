import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SullyBootSharedModule } from '../../shared';
import {
    TraitementService,
    TraitementPopupService,
    TraitementComponent,
    TraitementDetailComponent,
    TraitementDialogComponent,
    TraitementPopupComponent,
    TraitementDeletePopupComponent,
    TraitementDeleteDialogComponent,
    traitementRoute,
    traitementPopupRoute,
    TraitementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...traitementRoute,
    ...traitementPopupRoute,
];

@NgModule({
    imports: [
        SullyBootSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TraitementComponent,
        TraitementDetailComponent,
        TraitementDialogComponent,
        TraitementDeleteDialogComponent,
        TraitementPopupComponent,
        TraitementDeletePopupComponent,
    ],
    entryComponents: [
        TraitementComponent,
        TraitementDialogComponent,
        TraitementPopupComponent,
        TraitementDeleteDialogComponent,
        TraitementDeletePopupComponent,
    ],
    providers: [
        TraitementService,
        TraitementPopupService,
        TraitementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SullyBootTraitementModule {}
