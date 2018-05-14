/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SullyBootTestModule } from '../../../test.module';
import { NumeroAffaireComponent } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire.component';
import { NumeroAffaireService } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire.service';
import { NumeroAffaire } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire.model';

describe('Component Tests', () => {

    describe('NumeroAffaire Management Component', () => {
        let comp: NumeroAffaireComponent;
        let fixture: ComponentFixture<NumeroAffaireComponent>;
        let service: NumeroAffaireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [NumeroAffaireComponent],
                providers: [
                    NumeroAffaireService
                ]
            })
            .overrideTemplate(NumeroAffaireComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NumeroAffaireComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NumeroAffaireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NumeroAffaire(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.numeroAffaires[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
