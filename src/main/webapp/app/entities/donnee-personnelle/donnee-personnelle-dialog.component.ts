import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DonneePersonnelle } from './donnee-personnelle.model';
import { DonneePersonnellePopupService } from './donnee-personnelle-popup.service';
import { DonneePersonnelleService } from './donnee-personnelle.service';
import { Traitement, TraitementService } from '../traitement';

@Component({
    selector: 'jhi-donnee-personnelle-dialog',
    templateUrl: './donnee-personnelle-dialog.component.html'
})
export class DonneePersonnelleDialogComponent implements OnInit {

    donneePersonnelle: DonneePersonnelle;
    isSaving: boolean;

    traitements: Traitement[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private donneePersonnelleService: DonneePersonnelleService,
        private traitementService: TraitementService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.traitementService.query()
            .subscribe((res: HttpResponse<Traitement[]>) => { this.traitements = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.donneePersonnelle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.donneePersonnelleService.update(this.donneePersonnelle));
        } else {
            this.subscribeToSaveResponse(
                this.donneePersonnelleService.create(this.donneePersonnelle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DonneePersonnelle>>) {
        result.subscribe((res: HttpResponse<DonneePersonnelle>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DonneePersonnelle) {
        this.eventManager.broadcast({ name: 'donneePersonnelleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTraitementById(index: number, item: Traitement) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-donnee-personnelle-popup',
    template: ''
})
export class DonneePersonnellePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private donneePersonnellePopupService: DonneePersonnellePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.donneePersonnellePopupService
                    .open(DonneePersonnelleDialogComponent as Component, params['id']);
            } else {
                this.donneePersonnellePopupService
                    .open(DonneePersonnelleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
