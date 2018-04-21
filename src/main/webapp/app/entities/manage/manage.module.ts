import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetsportV2SharedModule } from '../../shared';
import {
    ManageService,
    ManagePopupService,
    ManageComponent,
    ManageDetailComponent,
    ManageDialogComponent,
    ManagePopupComponent,
    ManageDeletePopupComponent,
    ManageDeleteDialogComponent,
    manageRoute,
    managePopupRoute,
    ManageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...manageRoute,
    ...managePopupRoute,
];

@NgModule({
    imports: [
        BetsportV2SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ManageComponent,
        ManageDetailComponent,
        ManageDialogComponent,
        ManageDeleteDialogComponent,
        ManagePopupComponent,
        ManageDeletePopupComponent,
    ],
    entryComponents: [
        ManageComponent,
        ManageDialogComponent,
        ManagePopupComponent,
        ManageDeleteDialogComponent,
        ManageDeletePopupComponent,
    ],
    providers: [
        ManageService,
        ManagePopupService,
        ManageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BetsportV2ManageModule {}
