import { BaseEntity } from './../../shared';

export const enum CategorieFonctionnelle {
    'IDENTIFICATION_PERSONNELLE',
    'IDENTIFICATION_ELECTRONIQUE',
    'IDENTIFICATION_BIOMETRIQUE',
    'OTHER'
}

export const enum CategorieRGPD {
    'GENETIQUE',
    'BIOMETRIQUE',
    'SANTE',
    'ORIGINE',
    'OPINIOONS',
    'CONVICTIONS',
    'SYNDICALE',
    'VIE_SEXUELLE',
    'PENALE',
    'SECRET_PROFESSIONNEL',
    'COMMUNICATION_ELECTRONIQUE',
    'LOCALISATION',
    'FINANCIERE',
    'AUTRE'
}

export const enum Source {
    'PERSONNE',
    'RELATION',
    'CONSEILLER_PROFESSIONNEL',
    'EMPLOYEUR',
    'AUTRE_ENTREPRISE_PRIVEE',
    'SERVICE_PUBLIC',
    'JUSTICE_POLICE',
    'SECURITEE_SOCIALE',
    'BANQUE_ASSURANCE',
    'MARKETING',
    'AUTRE'
}

export const enum Destinataire {
    'AUCUN',
    'PERSONNE',
    'RELATION',
    'CONSEILLER_PROFESSIONNEL',
    'EMPLOYEUR',
    'AUTRE_ENTREPRISE_PRIVEE',
    'SERVICE_PUBLIC',
    'JUSTICE_POLICE',
    'SECURITEE_SOCIALE',
    'BANQUE_ASSURANCE',
    'MARKETING',
    'AUTRE'
}

export const enum NatureTransmissionPaysTiers {
    'DECISION_ADEQUATION',
    'GARANTIES_APPROPRIEES',
    'REGLES_ENTREPRISE',
    'DEROGATION',
    'CONDITION_ARTICLE_49_2'
}

export class DonneePersonnelle implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public categorieFonctionnelle?: CategorieFonctionnelle,
        public categorieRGPD?: CategorieRGPD,
        public source?: Source,
        public vulnerable?: boolean,
        public destinataire?: Destinataire,
        public natureTransmissionPaysTiers?: NatureTransmissionPaysTiers,
        public delaiConservation?: string,
        public echange?: string,
        public traitement?: BaseEntity,
    ) {
        this.vulnerable = false;
    }
}
