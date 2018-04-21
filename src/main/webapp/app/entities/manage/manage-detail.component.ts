import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Manage } from './manage.model';
import { ManageService } from './manage.service';

@Component({
    selector: 'jhi-manage-detail',
    templateUrl: './manage-detail.component.html'
})
export class ManageDetailComponent implements OnInit, OnDestroy {

    manage: Manage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private manageService: ManageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInManages();
    }

    load(id) {
        this.manageService.find(id)
            .subscribe((manageResponse: HttpResponse<Manage>) => {
                this.manage = manageResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInManages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'manageListModification',
            (response) => this.load(this.manage.id)
        );
    }
}
