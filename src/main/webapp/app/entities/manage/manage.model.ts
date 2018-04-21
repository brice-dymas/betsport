import { BaseEntity } from './../../shared';

export const enum State {
    'ACTIVE',
    'INACTIVE'
}

export class Manage implements BaseEntity {
    constructor(
        public id?: number,
        public managementDate?: any,
        public state?: State,
        public cashier?: BaseEntity,
        public cashDesk?: BaseEntity,
    ) {
    }
}
