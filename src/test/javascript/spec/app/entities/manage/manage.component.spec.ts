/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BetsportV2TestModule } from '../../../test.module';
import { ManageComponent } from '../../../../../../main/webapp/app/entities/manage/manage.component';
import { ManageService } from '../../../../../../main/webapp/app/entities/manage/manage.service';
import { Manage } from '../../../../../../main/webapp/app/entities/manage/manage.model';

describe('Component Tests', () => {

    describe('Manage Management Component', () => {
        let comp: ManageComponent;
        let fixture: ComponentFixture<ManageComponent>;
        let service: ManageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BetsportV2TestModule],
                declarations: [ManageComponent],
                providers: [
                    ManageService
                ]
            })
            .overrideTemplate(ManageComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ManageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ManageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Manage(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.manages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
