import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Assignment } from './assignment.model';
import { AssignmentPopupService } from './assignment-popup.service';
import { AssignmentService } from './assignment.service';
import { Employee, EmployeeService } from '../employee';
import { Period, PeriodService } from '../period';

@Component({
    selector: 'jhi-assignment-dialog',
    templateUrl: './assignment-dialog.component.html'
})
export class AssignmentDialogComponent implements OnInit {

    assignment: Assignment;
    isSaving: boolean;

    employees: Employee[];

    periods: Period[];
    assignedDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private assignmentService: AssignmentService,
        private employeeService: EmployeeService,
        private periodService: PeriodService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.employeeService.query()
            .subscribe((res: HttpResponse<Employee[]>) => { this.employees = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.periodService.query()
            .subscribe((res: HttpResponse<Period[]>) => { this.periods = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.assignment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.assignmentService.update(this.assignment));
        } else {
            this.subscribeToSaveResponse(
                this.assignmentService.create(this.assignment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Assignment>>) {
        result.subscribe((res: HttpResponse<Assignment>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Assignment) {
        this.eventManager.broadcast({ name: 'assignmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEmployeeById(index: number, item: Employee) {
        return item.id;
    }

    trackPeriodById(index: number, item: Period) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-assignment-popup',
    template: ''
})
export class AssignmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assignmentPopupService: AssignmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.assignmentPopupService
                    .open(AssignmentDialogComponent as Component, params['id']);
            } else {
                this.assignmentPopupService
                    .open(AssignmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
