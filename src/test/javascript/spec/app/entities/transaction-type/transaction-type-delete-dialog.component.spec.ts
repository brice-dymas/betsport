/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BetsportV2TestModule } from '../../../test.module';
import { TransactionTypeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type-delete-dialog.component';
import { TransactionTypeService } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type.service';

describe('Component Tests', () => {

    describe('TransactionType Management Delete Component', () => {
        let comp: TransactionTypeDeleteDialogComponent;
        let fixture: ComponentFixture<TransactionTypeDeleteDialogComponent>;
        let service: TransactionTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [TransactionTypeDeleteDialogComponent],
                providers: [
                    TransactionTypeService
                ]
            })
            .overrideTemplate(TransactionTypeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransactionTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
