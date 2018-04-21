import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Assignment } from './assignment.model';
import { AssignmentService } from './assignment.service';

@Component({
    selector: 'jhi-assignment-detail',
    templateUrl: './assignment-detail.component.html'
})
export class AssignmentDetailComponent implements OnInit, OnDestroy {

    assignment: Assignment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private assignmentService: AssignmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAssignments();
    }

    load(id) {
        this.assignmentService.find(id)
            .subscribe((assignmentResponse: HttpResponse<Assignment>) => {
                this.assignment = assignmentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAssignments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'assignmentListModification',
            (response) => this.load(this.assignment.id)
        );
    }
}
