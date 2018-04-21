import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Amount } from './amount.model';
import { AmountPopupService } from './amount-popup.service';
import { AmountService } from './amount.service';

@Component({
    selector: 'jhi-amount-dialog',
    templateUrl: './amount-dialog.component.html'
})
export class AmountDialogComponent implements OnInit {

    amount: Amount;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private amountService: AmountService,
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
        if (this.amount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.amountService.update(this.amount));
        } else {
            this.subscribeToSaveResponse(
                this.amountService.create(this.amount));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Amount>>) {
        result.subscribe((res: HttpResponse<Amount>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Amount) {
        this.eventManager.broadcast({ name: 'amountListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-amount-popup',
    template: ''
})
export class AmountPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private amountPopupService: AmountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.amountPopupService
                    .open(AmountDialogComponent as Component, params['id']);
            } else {
                this.amountPopupService
                    .open(AmountDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
