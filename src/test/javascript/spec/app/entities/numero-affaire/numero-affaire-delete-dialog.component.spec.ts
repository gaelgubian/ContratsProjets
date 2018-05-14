/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SullyBootTestModule } from '../../../test.module';
import { NumeroAffaireDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire-delete-dialog.component';
import { NumeroAffaireService } from '../../../../../../main/webapp/app/entities/numero-affaire/numero-affaire.service';

describe('Component Tests', () => {

    describe('NumeroAffaire Management Delete Component', () => {
        let comp: NumeroAffaireDeleteDialogComponent;
        let fixture: ComponentFixture<NumeroAffaireDeleteDialogComponent>;
        let service: NumeroAffaireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SullyBootTestModule],
                declarations: [NumeroAffaireDeleteDialogComponent],
                providers: [
                    NumeroAffaireService
                ]
            })
            .overrideTemplate(NumeroAffaireDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NumeroAffaireDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NumeroAffaireService);
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
