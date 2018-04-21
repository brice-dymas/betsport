import { BaseEntity } from './../../shared';

export const enum State {
    'ACTIVE',
    'INACTIVE'
}

export class Period implements BaseEntity {
    constructor(
        public id?: number,
        public beginingHour?: any,
        public endingHour?: any,
        public state?: State,
    ) {
    }
}
