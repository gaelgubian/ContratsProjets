import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SullyBootSharedModule } from '../../shared';
import {
    DependencyService,
    DependencyPopupService,
    DependencyComponent,
    DependencyDetailComponent,
    DependencyDialogComponent,
    DependencyPopupComponent,
    DependencyDeletePopupComponent,
    DependencyDeleteDialogComponent,
    dependencyRoute,
    dependencyPopupRoute,
    DependencyResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dependencyRoute,
    ...dependencyPopupRoute,
];

@NgModule({
    imports: [
        SullyBootSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DependencyComponent,
        DependencyDetailComponent,
        DependencyDialogComponent,
        DependencyDeleteDialogComponent,
        DependencyPopupComponent,
        DependencyDeletePopupComponent,
    ],
    entryComponents: [
        DependencyComponent,
        DependencyDialogComponent,
        DependencyPopupComponent,
        DependencyDeleteDialogComponent,
        DependencyDeletePopupComponent,
    ],
    providers: [
        DependencyService,
        DependencyPopupService,
        DependencyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SullyBootDependencyModule {}
