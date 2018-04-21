/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BetsportV2TestModule } from '../../../test.module';
import { PeriodComponent } from '../../../../../../main/webapp/app/entities/period/period.component';
import { PeriodService } from '../../../../../../main/webapp/app/entities/period/period.service';
import { Period } from '../../../../../../main/webapp/app/entities/period/period.model';

describe('Component Tests', () => {

    describe('Period Management Component', () => {
        let comp: PeriodComponent;
        let fixture: ComponentFixture<PeriodComponent>;
        let service: PeriodService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [PeriodComponent],
                providers: [
                    PeriodService
                ]
            })
            .overrideTemplate(PeriodComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeriodComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Period(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.periods[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
