import { BaseEntity } from './../../shared';

export const enum State {
    'ACTIVE',
    'INACTIVE'
}

export class CashDesk implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public cashAmount?: number,
        public state?: State,
    ) {
    }
}
