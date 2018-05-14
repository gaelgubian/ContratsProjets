import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Traitement } from './traitement.model';
import { TraitementPopupService } from './traitement-popup.service';
import { TraitementService } from './traitement.service';
import { Application, ApplicationService } from '../application';

@Component({
    selector: 'jhi-traitement-dialog',
    templateUrl: './traitement-dialog.component.html'
})
export class TraitementDialogComponent implements OnInit {

    traitement: Traitement;
    isSaving: boolean;

    applications: Application[];
    dateDebutDp: any;
    dateFinDp: any;
    dateMAJDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private traitementService: TraitementService,
        private applicationService: ApplicationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.applicationService.query()
            .subscribe((res: HttpResponse<Application[]>) => { this.applications = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.traitement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.traitementService.update(this.traitement));
        } else {
            this.subscribeToSaveResponse(
                this.traitementService.create(this.traitement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Traitement>>) {
        result.subscribe((res: HttpResponse<Traitement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Traitement) {
        this.eventManager.broadcast({ name: 'traitementListModification', content: 'OK'});
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
    selector: 'jhi-traitement-popup',
    template: ''
})
export class TraitementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private traitementPopupService: TraitementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.traitementPopupService
                    .open(TraitementDialogComponent as Component, params['id']);
            } else {
                this.traitementPopupService
                    .open(TraitementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
