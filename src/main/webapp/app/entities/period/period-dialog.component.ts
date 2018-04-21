import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Period } from './period.model';
import { PeriodPopupService } from './period-popup.service';
import { PeriodService } from './period.service';

@Component({
    selector: 'jhi-period-dialog',
    templateUrl: './period-dialog.component.html'
})
export class PeriodDialogComponent implements OnInit {

    period: Period;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private periodService: PeriodService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.period.id !== undefined) {
            this.subscribeToSaveResponse(
                this.periodService.update(this.period));
        } else {
            this.subscribeToSaveResponse(
                this.periodService.create(this.period));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Period>>) {
        result.subscribe((res: HttpResponse<Period>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Period) {
        this.eventManager.broadcast({ name: 'periodListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-period-popup',
    template: ''
})
export class PeriodPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private periodPopupService: PeriodPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.periodPopupService
                    .open(PeriodDialogComponent as Component, params['id']);
            } else {
                this.periodPopupService
                    .open(PeriodDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
