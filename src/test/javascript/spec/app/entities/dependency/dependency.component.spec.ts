/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SullyBootTestModule } from '../../../test.module';
import { DependencyComponent } from '../../../../../../main/webapp/app/entities/dependency/dependency.component';
import { DependencyService } from '../../../../../../main/webapp/app/entities/dependency/dependency.service';
import { Dependency } from '../../../../../../main/webapp/app/entities/dependency/dependency.model';

describe('Component Tests', () => {

    describe('Dependency Management Component', () => {
        let comp: DependencyComponent;
        let fixture: ComponentFixture<DependencyComponent>;
        let service: DependencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [DependencyComponent],
                providers: [
                    DependencyService
                ]
            })
            .overrideTemplate(DependencyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DependencyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DependencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Dependency(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dependencies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
