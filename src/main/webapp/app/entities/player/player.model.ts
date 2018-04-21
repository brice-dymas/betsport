import { BaseEntity } from './../../shared';

export const enum State {
    'ACTIVE',
    'INACTIVE'
}

export class Player implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public state?: State,
    ) {
    }
}
