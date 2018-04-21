/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BetsportV2TestModule } from '../../../test.module';
import { AssignmentDetailComponent } from '../../../../../../main/webapp/app/entities/assignment/assignment-detail.component';
import { AssignmentService } from '../../../../../../main/webapp/app/entities/assignment/assignment.service';
import { Assignment } from '../../../../../../main/webapp/app/entities/assignment/assignment.model';

describe('Component Tests', () => {

    describe('Assignment Management Detail Component', () => {
        let comp: AssignmentDetailComponent;
        let fixture: ComponentFixture<AssignmentDetailComponent>;
        let service: AssignmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [AssignmentDetailComponent],
                providers: [
                    AssignmentService
                ]
            })
            .overrideTemplate(AssignmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssignmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssignmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Assignment(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.assignment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
