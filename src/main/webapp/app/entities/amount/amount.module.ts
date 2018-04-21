import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetsportV2SharedModule } from '../../shared';
import {
    AmountService,
    AmountPopupService,
    AmountComponent,
    AmountDetailComponent,
    AmountDialogComponent,
    AmountPopupComponent,
    AmountDeletePopupComponent,
    AmountDeleteDialogComponent,
    amountRoute,
    amountPopupRoute,
    AmountResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...amountRoute,
    ...amountPopupRoute,
];

@NgModule({
    imports: [
        BetsportV2SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AmountComponent,
        AmountDetailComponent,
        AmountDialogComponent,
        AmountDeleteDialogComponent,
        AmountPopupComponent,
        AmountDeletePopupComponent,
    ],
    entryComponents: [
        AmountComponent,
        AmountDialogComponent,
        AmountPopupComponent,
        AmountDeleteDialogComponent,
        AmountDeletePopupComponent,
    ],
    providers: [
        AmountService,
        AmountPopupService,
        AmountResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BetsportV2AmountModule {}
