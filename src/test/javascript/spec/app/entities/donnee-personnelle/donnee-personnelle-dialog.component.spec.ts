/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SullyBootTestModule } from '../../../test.module';
import { DonneePersonnelleDialogComponent } from '../../../../../../main/webapp/app/entities/donnee-personnelle/donnee-personnelle-dialog.component';
import { DonneePersonnelleService } from '../../../../../../main/webapp/app/entities/donnee-personnelle/donnee-personnelle.service';
import { DonneePersonnelle } from '../../../../../../main/webapp/app/entities/donnee-personnelle/donnee-personnelle.model';
import { TraitementService } from '../../../../../../main/webapp/app/entities/traitement';

describe('Component Tests', () => {

    describe('DonneePersonnelle Management Dialog Component', () => {
        let comp: DonneePersonnelleDialogComponent;
        let fixture: ComponentFixture<DonneePersonnelleDialogComponent>;
        let service: DonneePersonnelleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [DonneePersonnelleDialogComponent],
                providers: [
                    TraitementService,
                    DonneePersonnelleService
                ]
            })
            .overrideTemplate(DonneePersonnelleDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DonneePersonnelleDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DonneePersonnelleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DonneePersonnelle(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.donneePersonnelle = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'donneePersonnelleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DonneePersonnelle();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.donneePersonnelle = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'donneePersonnelleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
