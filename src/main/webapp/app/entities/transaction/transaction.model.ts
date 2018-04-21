import { BaseEntity } from './../../shared';

export const enum State {
    'ACTIVE',
    'INACTIVE'
}

export class Transaction implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public transactionTime?: any,
        public transactionDate?: any,
        public amount?: number,
        public state?: State,
        public employee?: BaseEntity,
        public cashDesk?: BaseEntity,
        public player?: BaseEntity,
        public payment?: BaseEntity,
        public openingTransaction?: BaseEntity,
        public type?: BaseEntity,
    ) {
    }
}
