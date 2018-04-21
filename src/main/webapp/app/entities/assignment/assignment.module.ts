import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetsportV2SharedModule } from '../../shared';
import {
    AssignmentService,
    AssignmentPopupService,
    AssignmentComponent,
    AssignmentDetailComponent,
    AssignmentDialogComponent,
    AssignmentPopupComponent,
    AssignmentDeletePopupComponent,
    AssignmentDeleteDialogComponent,
    assignmentRoute,
    assignmentPopupRoute,
    AssignmentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...assignmentRoute,
    ...assignmentPopupRoute,
];

@NgModule({
    imports: [
        BetsportV2SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AssignmentComponent,
        AssignmentDetailComponent,
        AssignmentDialogComponent,
        AssignmentDeleteDialogComponent,
        AssignmentPopupComponent,
        AssignmentDeletePopupComponent,
    ],
    entryComponents: [
        AssignmentComponent,
        AssignmentDialogComponent,
        AssignmentPopupComponent,
        AssignmentDeleteDialogComponent,
        AssignmentDeletePopupComponent,
    ],
    providers: [
        AssignmentService,
        AssignmentPopupService,
        AssignmentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BetsportV2AssignmentModule {}
