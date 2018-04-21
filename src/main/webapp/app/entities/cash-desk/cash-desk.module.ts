import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetsportV2SharedModule } from '../../shared';
import {
    CashDeskService,
    CashDeskPopupService,
    CashDeskComponent,
    CashDeskDetailComponent,
    CashDeskDialogComponent,
    CashDeskPopupComponent,
    CashDeskDeletePopupComponent,
    CashDeskDeleteDialogComponent,
    cashDeskRoute,
    cashDeskPopupRoute,
    CashDeskResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...cashDeskRoute,
    ...cashDeskPopupRoute,
];

@NgModule({
    imports: [
        BetsportV2SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CashDeskComponent,
        CashDeskDetailComponent,
        CashDeskDialogComponent,
        CashDeskDeleteDialogComponent,
        CashDeskPopupComponent,
        CashDeskDeletePopupComponent,
    ],
    entryComponents: [
        CashDeskComponent,
        CashDeskDialogComponent,
        CashDeskPopupComponent,
        CashDeskDeleteDialogComponent,
        CashDeskDeletePopupComponent,
    ],
    providers: [
        CashDeskService,
        CashDeskPopupService,
        CashDeskResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BetsportV2CashDeskModule {}
