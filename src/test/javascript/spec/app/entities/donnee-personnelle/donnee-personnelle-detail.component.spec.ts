/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SullyBootTestModule } from '../../../test.module';
import { DonneePersonnelleDetailComponent } from '../../../../../../main/webapp/app/entities/donnee-personnelle/donnee-personnelle-detail.component';
import { DonneePersonnelleService } from '../../../../../../main/webapp/app/entities/donnee-personnelle/donnee-personnelle.service';
import { DonneePersonnelle } from '../../../../../../main/webapp/app/entities/donnee-personnelle/donnee-personnelle.model';

describe('Component Tests', () => {

    describe('DonneePersonnelle Management Detail Component', () => {
        let comp: DonneePersonnelleDetailComponent;
        let fixture: ComponentFixture<DonneePersonnelleDetailComponent>;
        let service: DonneePersonnelleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [DonneePersonnelleDetailComponent],
                providers: [
                    DonneePersonnelleService
                ]
            })
            .overrideTemplate(DonneePersonnelleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DonneePersonnelleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DonneePersonnelleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DonneePersonnelle(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.donneePersonnelle).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
