/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BetsportV2TestModule } from '../../../test.module';
import { ManageDetailComponent } from '../../../../../../main/webapp/app/entities/manage/manage-detail.component';
import { ManageService } from '../../../../../../main/webapp/app/entities/manage/manage.service';
import { Manage } from '../../../../../../main/webapp/app/entities/manage/manage.model';

describe('Component Tests', () => {

    describe('Manage Management Detail Component', () => {
        let comp: ManageDetailComponent;
        let fixture: ComponentFixture<ManageDetailComponent>;
        let service: ManageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [ManageDetailComponent],
                providers: [
                    ManageService
                ]
            })
            .overrideTemplate(ManageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ManageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ManageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Manage(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.manage).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
