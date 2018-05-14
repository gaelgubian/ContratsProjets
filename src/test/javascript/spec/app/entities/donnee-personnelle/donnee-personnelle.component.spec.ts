/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SullyBootTestModule } from '../../../test.module';
import { DonneePersonnelleComponent } from '../../../../../../main/webapp/app/entities/donnee-personnelle/donnee-personnelle.component';
import { DonneePersonnelleService } from '../../../../../../main/webapp/app/entities/donnee-personnelle/donnee-personnelle.service';
import { DonneePersonnelle } from '../../../../../../main/webapp/app/entities/donnee-personnelle/donnee-personnelle.model';

describe('Component Tests', () => {

    describe('DonneePersonnelle Management Component', () => {
        let comp: DonneePersonnelleComponent;
        let fixture: ComponentFixture<DonneePersonnelleComponent>;
        let service: DonneePersonnelleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [DonneePersonnelleComponent],
                providers: [
                    DonneePersonnelleService
                ]
            })
            .overrideTemplate(DonneePersonnelleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DonneePersonnelleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DonneePersonnelleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DonneePersonnelle(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.donneePersonnelles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
