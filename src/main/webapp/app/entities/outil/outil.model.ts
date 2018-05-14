import { BaseEntity } from './../../shared';

export const enum CategorieOutil {
    'CONCEPTION',
    'DEVELOPPEMENT',
    'TEST',
    'MANAGEMENT',
    'DOCUMENT',
    'ANALYSE',
    'DEPLOIEMENT',
    'SGBD',
    'SERVER_APPLICATION',
    'OTHER'
}

export class Outil implements BaseEntity {
    constructor(
        public id?: number,
        public imageContentType?: string,
        public image?: any,
        public categorie?: CategorieOutil,
        public nom?: string,
        public url?: string,
        public application?: BaseEntity,
    ) {
    }
}
