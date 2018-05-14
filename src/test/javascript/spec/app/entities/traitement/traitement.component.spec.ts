/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SullyBootTestModule } from '../../../test.module';
import { TraitementComponent } from '../../../../../../main/webapp/app/entities/traitement/traitement.component';
import { TraitementService } from '../../../../../../main/webapp/app/entities/traitement/traitement.service';
import { Traitement } from '../../../../../../main/webapp/app/entities/traitement/traitement.model';

describe('Component Tests', () => {

    describe('Traitement Management Component', () => {
        let comp: TraitementComponent;
        let fixture: ComponentFixture<TraitementComponent>;
        let service: TraitementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [TraitementComponent],
                providers: [
                    TraitementService
                ]
            })
            .overrideTemplate(TraitementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TraitementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TraitementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Traitement(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.traitements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
