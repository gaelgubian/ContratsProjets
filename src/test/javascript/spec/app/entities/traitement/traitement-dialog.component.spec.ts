/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SullyBootTestModule } from '../../../test.module';
import { TraitementDialogComponent } from '../../../../../../main/webapp/app/entities/traitement/traitement-dialog.component';
import { TraitementService } from '../../../../../../main/webapp/app/entities/traitement/traitement.service';
import { Traitement } from '../../../../../../main/webapp/app/entities/traitement/traitement.model';
import { ApplicationService } from '../../../../../../main/webapp/app/entities/application';

describe('Component Tests', () => {

    describe('Traitement Management Dialog Component', () => {
        let comp: TraitementDialogComponent;
        let fixture: ComponentFixture<TraitementDialogComponent>;
        let service: TraitementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [TraitementDialogComponent],
                providers: [
                    ApplicationService,
                    TraitementService
                ]
            })
            .overrideTemplate(TraitementDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TraitementDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TraitementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Traitement(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.traitement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'traitementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Traitement();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.traitement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'traitementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
