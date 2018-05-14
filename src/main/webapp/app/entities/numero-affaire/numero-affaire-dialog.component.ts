import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NumeroAffaire } from './numero-affaire.model';
import { NumeroAffairePopupService } from './numero-affaire-popup.service';
import { NumeroAffaireService } from './numero-affaire.service';
import { Projet, ProjetService } from '../projet';

@Component({
    selector: 'jhi-numero-affaire-dialog',
    templateUrl: './numero-affaire-dialog.component.html'
})
export class NumeroAffaireDialogComponent implements OnInit {

    numeroAffaire: NumeroAffaire;
    isSaving: boolean;

    projets: Projet[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private numeroAffaireService: NumeroAffaireService,
        private projetService: ProjetService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.projetService.query()
            .subscribe((res: HttpResponse<Projet[]>) => { this.projets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.numeroAffaire.id !== undefined) {
            this.subscribeToSaveResponse(
                this.numeroAffaireService.update(this.numeroAffaire));
        } else {
            this.subscribeToSaveResponse(
                this.numeroAffaireService.create(this.numeroAffaire));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NumeroAffaire>>) {
        result.subscribe((res: HttpResponse<NumeroAffaire>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NumeroAffaire) {
        this.eventManager.broadcast({ name: 'numeroAffaireListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProjetById(index: number, item: Projet) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-numero-affaire-popup',
    template: ''
})
export class NumeroAffairePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private numeroAffairePopupService: NumeroAffairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.numeroAffairePopupService
                    .open(NumeroAffaireDialogComponent as Component, params['id']);
            } else {
                this.numeroAffairePopupService
                    .open(NumeroAffaireDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
