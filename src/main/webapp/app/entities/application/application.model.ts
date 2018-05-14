import { BaseEntity } from './../../shared';

export const enum Langage {
    'JAVA',
    'JAVASCRIPT',
    'PHP',
    'DOTNET',
    'DELPHI',
    'C',
    'CPP',
    'OTHER'
}

export class Application implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public nomCourt?: string,
        public langagePrincipal?: Langage,
        public imageContentType?: string,
        public image?: any,
        public projet?: BaseEntity,
        public dependencies?: BaseEntity[],
        public outils?: BaseEntity[],
        public traitements?: BaseEntity[],
    ) {
    }
}
