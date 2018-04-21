import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Assignment } from './assignment.model';
import { AssignmentService } from './assignment.service';

@Injectable()
export class AssignmentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private assignmentService: AssignmentService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.assignmentService.find(id)
                    .subscribe((assignmentResponse: HttpResponse<Assignment>) => {
                        const assignment: Assignment = assignmentResponse.body;
                        if (assignment.assignedDate) {
                            assignment.assignedDate = {
                                year: assignment.assignedDate.getFullYear(),
                                month: assignment.assignedDate.getMonth() + 1,
                                day: assignment.assignedDate.getDate()
                            };
                        }
                        assignment.assignedTime = this.datePipe
                            .transform(assignment.assignedTime, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.assignmentModalRef(component, assignment);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.assignmentModalRef(component, new Assignment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    assignmentModalRef(component: Component, assignment: Assignment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.assignment = assignment;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
