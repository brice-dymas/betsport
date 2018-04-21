/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BetsportV2TestModule } from '../../../test.module';
import { AssignmentComponent } from '../../../../../../main/webapp/app/entities/assignment/assignment.component';
import { AssignmentService } from '../../../../../../main/webapp/app/entities/assignment/assignment.service';
import { Assignment } from '../../../../../../main/webapp/app/entities/assignment/assignment.model';

describe('Component Tests', () => {

    describe('Assignment Management Component', () => {
        let comp: AssignmentComponent;
        let fixture: ComponentFixture<AssignmentComponent>;
        let service: AssignmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [AssignmentComponent],
                providers: [
                    AssignmentService
                ]
            })
            .overrideTemplate(AssignmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssignmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssignmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Assignment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.assignments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
