import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Outil } from './outil.model';
import { OutilPopupService } from './outil-popup.service';
import { OutilService } from './outil.service';
import { Application, ApplicationService } from '../application';

@Component({
    selector: 'jhi-outil-dialog',
    templateUrl: './outil-dialog.component.html'
})
export class OutilDialogComponent implements OnInit {

    outil: Outil;
    isSaving: boolean;

    applications: Application[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private outilService: OutilService,
        private applicationService: ApplicationService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.applicationService.query()
            .subscribe((res: HttpResponse<Application[]>) => { this.applications = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.outil, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.outil.id !== undefined) {
            this.subscribeToSaveResponse(
                this.outilService.update(this.outil));
        } else {
            this.subscribeToSaveResponse(
                this.outilService.create(this.outil));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Outil>>) {
        result.subscribe((res: HttpResponse<Outil>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Outil) {
        this.eventManager.broadcast({ name: 'outilListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackApplicationById(index: number, item: Application) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-outil-popup',
    template: ''
})
export class OutilPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private outilPopupService: OutilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.outilPopupService
                    .open(OutilDialogComponent as Component, params['id']);
            } else {
                this.outilPopupService
                    .open(OutilDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
