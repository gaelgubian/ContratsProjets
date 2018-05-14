import { BaseEntity } from './../../shared';

export class NumeroAffaire implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public projet?: BaseEntity,
    ) {
    }
}
