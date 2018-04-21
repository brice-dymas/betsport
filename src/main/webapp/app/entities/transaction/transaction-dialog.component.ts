import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Transaction } from './transaction.model';
import { TransactionPopupService } from './transaction-popup.service';
import { TransactionService } from './transaction.service';
import { Employee, EmployeeService } from '../employee';
import { CashDesk, CashDeskService } from '../cash-desk';
import { Player, PlayerService } from '../player';
import { TransactionType, TransactionTypeService } from '../transaction-type';

@Component({
    selector: 'jhi-transaction-dialog',
    templateUrl: './transaction-dialog.component.html'
})
export class TransactionDialogComponent implements OnInit {

    transaction: Transaction;
    isSaving: boolean;

    employees: Employee[];

    cashdesks: CashDesk[];

    players: Player[];

    transactions: Transaction[];

    transactiontypes: TransactionType[];
    transactionDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private transactionService: TransactionService,
        private employeeService: EmployeeService,
        private cashDeskService: CashDeskService,
        private playerService: PlayerService,
        private transactionTypeService: TransactionTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.employeeService.query()
            .subscribe((res: HttpResponse<Employee[]>) => { this.employees = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.cashDeskService.query()
            .subscribe((res: HttpResponse<CashDesk[]>) => { this.cashdesks = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.playerService.query()
            .subscribe((res: HttpResponse<Player[]>) => { this.players = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.transactionService.query()
            .subscribe((res: HttpResponse<Transaction[]>) => { this.transactions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.transactionTypeService.query()
            .subscribe((res: HttpResponse<TransactionType[]>) => { this.transactiontypes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.transaction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.transactionService.update(this.transaction));
        } else {
            this.subscribeToSaveResponse(
                this.transactionService.create(this.transaction));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Transaction>>) {
        result.subscribe((res: HttpResponse<Transaction>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Transaction) {
        this.eventManager.broadcast({ name: 'transactionListModification', content: 'OK'});
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

    trackPlayerById(index: number, item: Player) {
        return item.id;
    }

    trackTransactionById(index: number, item: Transaction) {
        return item.id;
    }

    trackTransactionTypeById(index: number, item: TransactionType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-transaction-popup',
    template: ''
})
export class TransactionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionPopupService: TransactionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.transactionPopupService
                    .open(TransactionDialogComponent as Component, params['id']);
            } else {
                this.transactionPopupService
                    .open(TransactionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
