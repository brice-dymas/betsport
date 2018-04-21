import { BaseEntity } from './../../shared';

export const enum State {
    'ACTIVE',
    'INACTIVE'
}

export class Assignment implements BaseEntity {
    constructor(
        public id?: number,
        public assignedDate?: any,
        public assignedTime?: any,
        public state?: State,
        public employee?: BaseEntity,
        public period?: BaseEntity,
    ) {
    }
}
