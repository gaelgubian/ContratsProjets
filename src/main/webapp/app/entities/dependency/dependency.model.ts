import { BaseEntity } from './../../shared';

export class Dependency implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public version?: string,
        public application?: BaseEntity,
    ) {
    }
}
