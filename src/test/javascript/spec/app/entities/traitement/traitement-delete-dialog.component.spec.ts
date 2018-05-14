/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SullyBootTestModule } from '../../../test.module';
import { TraitementDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/traitement/traitement-delete-dialog.component';
import { TraitementService } from '../../../../../../main/webapp/app/entities/traitement/traitement.service';

describe('Component Tests', () => {

    describe('Traitement Management Delete Component', () => {
        let comp: TraitementDeleteDialogComponent;
        let fixture: ComponentFixture<TraitementDeleteDialogComponent>;
        let service: TraitementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [TraitementDeleteDialogComponent],
                providers: [
                    TraitementService
                ]
            })
            .overrideTemplate(TraitementDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TraitementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TraitementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
