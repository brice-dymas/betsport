/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BetsportV2TestModule } from '../../../test.module';
import { TransactionTypeDetailComponent } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type-detail.component';
import { TransactionTypeService } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type.service';
import { TransactionType } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type.model';

describe('Component Tests', () => {

    describe('TransactionType Management Detail Component', () => {
        let comp: TransactionTypeDetailComponent;
        let fixture: ComponentFixture<TransactionTypeDetailComponent>;
        let service: TransactionTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [TransactionTypeDetailComponent],
                providers: [
                    TransactionTypeService
                ]
            })
            .overrideTemplate(TransactionTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransactionTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TransactionType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.transactionType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
