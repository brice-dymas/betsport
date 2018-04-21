/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BetsportV2TestModule } from '../../../test.module';
import { AmountDetailComponent } from '../../../../../../main/webapp/app/entities/amount/amount-detail.component';
import { AmountService } from '../../../../../../main/webapp/app/entities/amount/amount.service';
import { Amount } from '../../../../../../main/webapp/app/entities/amount/amount.model';

describe('Component Tests', () => {

    describe('Amount Management Detail Component', () => {
        let comp: AmountDetailComponent;
        let fixture: ComponentFixture<AmountDetailComponent>;
        let service: AmountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [AmountDetailComponent],
                providers: [
                    AmountService
                ]
            })
            .overrideTemplate(AmountDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AmountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Amount(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.amount).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
