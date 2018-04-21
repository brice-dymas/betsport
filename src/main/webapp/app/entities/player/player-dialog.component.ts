import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Player } from './player.model';
import { PlayerPopupService } from './player-popup.service';
import { PlayerService } from './player.service';

@Component({
    selector: 'jhi-player-dialog',
    templateUrl: './player-dialog.component.html'
})
export class PlayerDialogComponent implements OnInit {

    player: Player;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private playerService: PlayerService,
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
        if (this.player.id !== undefined) {
            this.subscribeToSaveResponse(
                this.playerService.update(this.player));
        } else {
            this.subscribeToSaveResponse(
                this.playerService.create(this.player));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Player>>) {
        result.subscribe((res: HttpResponse<Player>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Player) {
        this.eventManager.broadcast({ name: 'playerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-player-popup',
    template: ''
})
export class PlayerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private playerPopupService: PlayerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.playerPopupService
                    .open(PlayerDialogComponent as Component, params['id']);
            } else {
                this.playerPopupService
                    .open(PlayerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
