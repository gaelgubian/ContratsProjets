import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SullyBootClientModule } from './client/client.module';
import { SullyBootProjetModule } from './projet/projet.module';
import { SullyBootNumeroAffaireModule } from './numero-affaire/numero-affaire.module';
import { SullyBootApplicationModule } from './application/application.module';
import { SullyBootDependencyModule } from './dependency/dependency.module';
import { SullyBootOutilModule } from './outil/outil.module';
import { SullyBootTraitementModule } from './traitement/traitement.module';
import { SullyBootDonneePersonnelleModule } from './donnee-personnelle/donnee-personnelle.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SullyBootClientModule,
        SullyBootProjetModule,
        SullyBootNumeroAffaireModule,
        SullyBootApplicationModule,
        SullyBootDependencyModule,
        SullyBootOutilModule,
        SullyBootTraitementModule,
        SullyBootDonneePersonnelleModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SullyBootEntityModule {}
