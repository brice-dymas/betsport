import { BaseEntity } from './../../shared';

export const enum ActionType {
    'ADD',
    'REMOVE',
    'NOTHING'
}

export class TransactionType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public action?: ActionType,
    ) {
    }
}
