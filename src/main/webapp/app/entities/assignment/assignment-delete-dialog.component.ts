import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Assignment } from './assignment.model';
import { AssignmentPopupService } from './assignment-popup.service';
import { AssignmentService } from './assignment.service';

@Component({
    selector: 'jhi-assignment-delete-dialog',
    templateUrl: './assignment-delete-dialog.component.html'
})
export class AssignmentDeleteDialogComponent {

    assignment: Assignment;

    constructor(
        private assignmentService: AssignmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.assignmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'assignmentListModification',
                content: 'Deleted an assignment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-assignment-delete-popup',
    template: ''
})
export class AssignmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assignmentPopupService: AssignmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.assignmentPopupService
                .open(AssignmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
