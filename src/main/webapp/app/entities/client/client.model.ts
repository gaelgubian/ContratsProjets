import { BaseEntity } from './../../shared';

export class Client implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public nomCourt?: string,
        public imageContentType?: string,
        public image?: any,
        public projets?: BaseEntity[],
    ) {
    }
}
