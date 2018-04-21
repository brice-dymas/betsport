import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Manage } from './manage.model';
import { ManagePopupService } from './manage-popup.service';
import { ManageService } from './manage.service';
import { Employee, EmployeeService } from '../employee';
import { CashDesk, CashDeskService } from '../cash-desk';

@Component({
    selector: 'jhi-manage-dialog',
    templateUrl: './manage-dialog.component.html'
})
export class ManageDialogComponent implements OnInit {

    manage: Manage;
    isSaving: boolean;

    employees: Employee[];

    cashdesks: CashDesk[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private manageService: ManageService,
        private employeeService: EmployeeService,
        private cashDeskService: CashDeskService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.employeeService.query()
            .subscribe((res: HttpResponse<Employee[]>) => { this.employees = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.cashDeskService.query()
            .subscribe((res: HttpResponse<CashDesk[]>) => { this.cashdesks = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.manage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.manageService.update(this.manage));
        } else {
            this.subscribeToSaveResponse(
                this.manageService.create(this.manage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Manage>>) {
        result.subscribe((res: HttpResponse<Manage>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Manage) {
        this.eventManager.broadcast({ name: 'manageListModification', content: 'OK'});
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

    trackCashDeskById(index: number, item: CashDesk) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-manage-popup',
    template: ''
})
export class ManagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private managePopupService: ManagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.managePopupService
                    .open(ManageDialogComponent as Component, params['id']);
            } else {
                this.managePopupService
                    .open(ManageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
