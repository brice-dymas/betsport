import { BaseEntity, User } from './../../shared';

export const enum State {
    'ACTIVE',
    'INACTIVE'
}

export class Employee implements BaseEntity {
    constructor(
        public id?: number,
        public photoContentType?: string,
        public photo?: any,
        public phoneNumber?: string,
        public state?: State,
        public user?: User,
    ) {
    }
}
