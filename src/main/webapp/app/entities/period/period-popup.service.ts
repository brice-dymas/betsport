import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Period } from './period.model';
import { PeriodService } from './period.service';

@Injectable()
export class PeriodPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private periodService: PeriodService

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
                this.periodService.find(id)
                    .subscribe((periodResponse: HttpResponse<Period>) => {
                        const period: Period = periodResponse.body;
                        period.beginingHour = this.datePipe
                            .transform(period.beginingHour, 'yyyy-MM-ddTHH:mm:ss');
                        period.endingHour = this.datePipe
                            .transform(period.endingHour, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.periodModalRef(component, period);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.periodModalRef(component, new Period());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    periodModalRef(component: Component, period: Period): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.period = period;
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
