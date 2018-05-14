import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Projet } from './projet.model';
import { ProjetPopupService } from './projet-popup.service';
import { ProjetService } from './projet.service';
import { Client, ClientService } from '../client';

@Component({
    selector: 'jhi-projet-dialog',
    templateUrl: './projet-dialog.component.html'
})
export class ProjetDialogComponent implements OnInit {

    projet: Projet;
    isSaving: boolean;

    clients: Client[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private projetService: ProjetService,
        private clientService: ClientService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.clientService.query()
            .subscribe((res: HttpResponse<Client[]>) => { this.clients = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.dataUtils.clearInputImage(this.projet, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.projet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.projetService.update(this.projet));
        } else {
            this.subscribeToSaveResponse(
                this.projetService.create(this.projet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Projet>>) {
        result.subscribe((res: HttpResponse<Projet>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Projet) {
        this.eventManager.broadcast({ name: 'projetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackClientById(index: number, item: Client) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-projet-popup',
    template: ''
})
export class ProjetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projetPopupService: ProjetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projetPopupService
                    .open(ProjetDialogComponent as Component, params['id']);
            } else {
                this.projetPopupService
                    .open(ProjetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
