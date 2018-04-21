import { BaseEntity } from './../../shared';

export const enum State {
    'ACTIVE',
    'INACTIVE'
}

export class Amount implements BaseEntity {
    constructor(
        public id?: number,
        public value?: number,
        public state?: State,
    ) {
    }
}
