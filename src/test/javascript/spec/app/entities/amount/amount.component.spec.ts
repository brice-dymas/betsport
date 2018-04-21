/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BetsportV2TestModule } from '../../../test.module';
import { AmountComponent } from '../../../../../../main/webapp/app/entities/amount/amount.component';
import { AmountService } from '../../../../../../main/webapp/app/entities/amount/amount.service';
import { Amount } from '../../../../../../main/webapp/app/entities/amount/amount.model';

describe('Component Tests', () => {

    describe('Amount Management Component', () => {
        let comp: AmountComponent;
        let fixture: ComponentFixture<AmountComponent>;
        let service: AmountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [AmountComponent],
                providers: [
                    AmountService
                ]
            })
            .overrideTemplate(AmountComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AmountComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Amount(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.amounts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
