import { BaseEntity } from './../../shared';

export const enum Finalite {
    'GENERAL_ADMIN_PERSONNEL',
    'GENERAL_GESTION_PERSONNEL',
    'GENERAL_PLANIF_ACTIVITE',
    'OTHER'
}

export const enum FondementTraitement {
    'CONSENTEMENT',
    'NECESSITE_CONTRACTUEL',
    'OBLIGATION_LEGAL',
    'OTHER'
}

export const enum TypeTraitement {
    'NORMAL',
    'PROFILAGE_PREVISION',
    'JURIDIQUE',
    'SURVEILLANCE',
    'GRANDE_ECHELLE',
    'COMBINAISONS',
    'EXERCICE_DROIT',
    'NOUVELLES_TECHNOLOGIES',
    'SURVEILLANCE_PUBLIC'
}

export class Traitement implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public description?: string,
        public finalite?: Finalite,
        public fondementTraitement?: FondementTraitement,
        public typeTraitement?: TypeTraitement,
        public sousTraitant?: string,
        public technologie?: string,
        public risque?: string,
        public mesureSecurite?: string,
        public documentation?: string,
        public aipd?: string,
        public information?: string,
        public procedureExerciceDroits?: string,
        public remarques?: string,
        public dateDebut?: any,
        public dateFin?: any,
        public dateMAJ?: any,
        public application?: BaseEntity,
        public donneesPersonnelles?: BaseEntity[],
    ) {
    }
}
