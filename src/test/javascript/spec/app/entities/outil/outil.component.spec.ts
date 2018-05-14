/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SullyBootTestModule } from '../../../test.module';
import { OutilComponent } from '../../../../../../main/webapp/app/entities/outil/outil.component';
import { OutilService } from '../../../../../../main/webapp/app/entities/outil/outil.service';
import { Outil } from '../../../../../../main/webapp/app/entities/outil/outil.model';

describe('Component Tests', () => {

    describe('Outil Management Component', () => {
        let comp: OutilComponent;
        let fixture: ComponentFixture<OutilComponent>;
        let service: OutilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [OutilComponent],
                providers: [
                    OutilService
                ]
            })
            .overrideTemplate(OutilComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OutilComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OutilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Outil(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.outils[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
