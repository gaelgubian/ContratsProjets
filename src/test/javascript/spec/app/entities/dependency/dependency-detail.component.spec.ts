/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SullyBootTestModule } from '../../../test.module';
import { DependencyDetailComponent } from '../../../../../../main/webapp/app/entities/dependency/dependency-detail.component';
import { DependencyService } from '../../../../../../main/webapp/app/entities/dependency/dependency.service';
import { Dependency } from '../../../../../../main/webapp/app/entities/dependency/dependency.model';

describe('Component Tests', () => {

    describe('Dependency Management Detail Component', () => {
        let comp: DependencyDetailComponent;
        let fixture: ComponentFixture<DependencyDetailComponent>;
        let service: DependencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [DependencyDetailComponent],
                providers: [
                    DependencyService
                ]
            })
            .overrideTemplate(DependencyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DependencyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DependencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Dependency(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dependency).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
