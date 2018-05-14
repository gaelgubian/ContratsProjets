import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Traitement } from './traitement.model';
import { TraitementPopupService } from './traitement-popup.service';
import { TraitementService } from './traitement.service';

@Component({
    selector: 'jhi-traitement-delete-dialog',
    templateUrl: './traitement-delete-dialog.component.html'
})
export class TraitementDeleteDialogComponent {

    traitement: Traitement;

    constructor(
        private traitementService: TraitementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.traitementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'traitementListModification',
                content: 'Deleted an traitement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-traitement-delete-popup',
    template: ''
})
export class TraitementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private traitementPopupService: TraitementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.traitementPopupService
                .open(TraitementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
