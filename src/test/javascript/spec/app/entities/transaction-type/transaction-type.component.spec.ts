/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BetsportV2TestModule } from '../../../test.module';
import { TransactionTypeComponent } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type.component';
import { TransactionTypeService } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type.service';
import { TransactionType } from '../../../../../../main/webapp/app/entities/transaction-type/transaction-type.model';

describe('Component Tests', () => {

    describe('TransactionType Management Component', () => {
        let comp: TransactionTypeComponent;
        let fixture: ComponentFixture<TransactionTypeComponent>;
        let service: TransactionTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [TransactionTypeComponent],
                providers: [
                    TransactionTypeService
                ]
            })
            .overrideTemplate(TransactionTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransactionTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TransactionType(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.transactionTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
