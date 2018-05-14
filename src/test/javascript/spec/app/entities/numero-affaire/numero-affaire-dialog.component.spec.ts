/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SullyBootTestModule } from '../../../test.module';
import { NumeroAffaireDialogComponent } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire-dialog.component';
import { NumeroAffaireService } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire.service';
import { NumeroAffaire } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire.model';
import { ProjetService } from '../../../../../../main/webapp/app/entities/projet';

describe('Component Tests', () => {

    describe('NumeroAffaire Management Dialog Component', () => {
        let comp: NumeroAffaireDialogComponent;
        let fixture: ComponentFixture<NumeroAffaireDialogComponent>;
        let service: NumeroAffaireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [NumeroAffaireDialogComponent],
                providers: [
                    ProjetService,
                    NumeroAffaireService
                ]
            })
            .overrideTemplate(NumeroAffaireDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NumeroAffaireDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NumeroAffaireService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NumeroAffaire(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.numeroAffaire = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'numeroAffaireListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NumeroAffaire();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.numeroAffaire = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'numeroAffaireListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
