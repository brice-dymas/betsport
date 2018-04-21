import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CashDesk } from './cash-desk.model';
import { CashDeskPopupService } from './cash-desk-popup.service';
import { CashDeskService } from './cash-desk.service';

@Component({
    selector: 'jhi-cash-desk-dialog',
    templateUrl: './cash-desk-dialog.component.html'
})
export class CashDeskDialogComponent implements OnInit {

    cashDesk: CashDesk;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private cashDeskService: CashDeskService,
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
        if (this.cashDesk.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cashDeskService.update(this.cashDesk));
        } else {
            this.subscribeToSaveResponse(
                this.cashDeskService.create(this.cashDesk));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CashDesk>>) {
        result.subscribe((res: HttpResponse<CashDesk>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CashDesk) {
        this.eventManager.broadcast({ name: 'cashDeskListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-cash-desk-popup',
    template: ''
})
export class CashDeskPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cashDeskPopupService: CashDeskPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cashDeskPopupService
                    .open(CashDeskDialogComponent as Component, params['id']);
            } else {
                this.cashDeskPopupService
                    .open(CashDeskDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
