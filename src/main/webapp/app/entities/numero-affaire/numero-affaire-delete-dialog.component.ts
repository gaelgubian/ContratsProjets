import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NumeroAffaire } from './numero-affaire.model';
import { NumeroAffairePopupService } from './numero-affaire-popup.service';
import { NumeroAffaireService } from './numero-affaire.service';

@Component({
    selector: 'jhi-numero-affaire-delete-dialog',
    templateUrl: './numero-affaire-delete-dialog.component.html'
})
export class NumeroAffaireDeleteDialogComponent {

    numeroAffaire: NumeroAffaire;

    constructor(
        private numeroAffaireService: NumeroAffaireService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.numeroAffaireService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'numeroAffaireListModification',
                content: 'Deleted an numeroAffaire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-numero-affaire-delete-popup',
    template: ''
})
export class NumeroAffaireDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private numeroAffairePopupService: NumeroAffairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.numeroAffairePopupService
                .open(NumeroAffaireDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
