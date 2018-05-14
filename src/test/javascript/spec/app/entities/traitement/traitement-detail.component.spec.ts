/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SullyBootTestModule } from '../../../test.module';
import { TraitementDetailComponent } from '../../../../../../main/webapp/app/entities/traitement/traitement-detail.component';
import { TraitementService } from '../../../../../../main/webapp/app/entities/traitement/traitement.service';
import { Traitement } from '../../../../../../main/webapp/app/entities/traitement/traitement.model';

describe('Component Tests', () => {

    describe('Traitement Management Detail Component', () => {
        let comp: TraitementDetailComponent;
        let fixture: ComponentFixture<TraitementDetailComponent>;
        let service: TraitementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [TraitementDetailComponent],
                providers: [
                    TraitementService
                ]
            })
            .overrideTemplate(TraitementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TraitementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TraitementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Traitement(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.traitement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
