import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetsportV2SharedModule } from '../../shared';
import {
    TransactionTypeService,
    TransactionTypePopupService,
    TransactionTypeComponent,
    TransactionTypeDetailComponent,
    TransactionTypeDialogComponent,
    TransactionTypePopupComponent,
    TransactionTypeDeletePopupComponent,
    TransactionTypeDeleteDialogComponent,
    transactionTypeRoute,
    transactionTypePopupRoute,
    TransactionTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...transactionTypeRoute,
    ...transactionTypePopupRoute,
];

@NgModule({
    imports: [
        BetsportV2SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TransactionTypeComponent,
        TransactionTypeDetailComponent,
        TransactionTypeDialogComponent,
        TransactionTypeDeleteDialogComponent,
        TransactionTypePopupComponent,
        TransactionTypeDeletePopupComponent,
    ],
    entryComponents: [
        TransactionTypeComponent,
        TransactionTypeDialogComponent,
        TransactionTypePopupComponent,
        TransactionTypeDeleteDialogComponent,
        TransactionTypeDeletePopupComponent,
    ],
    providers: [
        TransactionTypeService,
        TransactionTypePopupService,
        TransactionTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BetsportV2TransactionTypeModule {}
