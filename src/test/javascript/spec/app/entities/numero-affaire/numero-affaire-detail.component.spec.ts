/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SullyBootTestModule } from '../../../test.module';
import { NumeroAffaireDetailComponent } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire-detail.component';
import { NumeroAffaireService } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire.service';
import { NumeroAffaire } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire.model';

describe('Component Tests', () => {

    describe('NumeroAffaire Management Detail Component', () => {
        let comp: NumeroAffaireDetailComponent;
        let fixture: ComponentFixture<NumeroAffaireDetailComponent>;
        let service: NumeroAffaireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [NumeroAffaireDetailComponent],
                providers: [
                    NumeroAffaireService
                ]
            })
            .overrideTemplate(NumeroAffaireDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NumeroAffaireDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NumeroAffaireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NumeroAffaire(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.numeroAffaire).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
