import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Manage } from './manage.model';
import { ManagePopupService } from './manage-popup.service';
import { ManageService } from './manage.service';

@Component({
    selector: 'jhi-manage-delete-dialog',
    templateUrl: './manage-delete-dialog.component.html'
})
export class ManageDeleteDialogComponent {

    manage: Manage;

    constructor(
        private manageService: ManageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.manageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'manageListModification',
                content: 'Deleted an manage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-manage-delete-popup',
    template: ''
})
export class ManageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private managePopupService: ManagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.managePopupService
                .open(ManageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
