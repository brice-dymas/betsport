/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BetsportV2TestModule } from '../../../test.module';
import { PeriodDetailComponent } from '../../../../../../main/webapp/app/entities/period/period-detail.component';
import { PeriodService } from '../../../../../../main/webapp/app/entities/period/period.service';
import { Period } from '../../../../../../main/webapp/app/entities/period/period.model';

describe('Component Tests', () => {

    describe('Period Management Detail Component', () => {
        let comp: PeriodDetailComponent;
        let fixture: ComponentFixture<PeriodDetailComponent>;
        let service: PeriodService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [PeriodDetailComponent],
                providers: [
                    PeriodService
                ]
            })
            .overrideTemplate(PeriodDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeriodDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Period(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.period).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
