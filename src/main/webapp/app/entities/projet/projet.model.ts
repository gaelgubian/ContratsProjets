import { BaseEntity } from './../../shared';

export class Projet implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public nomCourt?: string,
        public imageContentType?: string,
        public image?: any,
        public client?: BaseEntity,
        public numerosAffaires?: BaseEntity[],
        public applications?: BaseEntity[],
    ) {
    }
}
