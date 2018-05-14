import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DonneePersonnelle } from './donnee-personnelle.model';
import { DonneePersonnellePopupService } from './donnee-personnelle-popup.service';
import { DonneePersonnelleService } from './donnee-personnelle.service';

@Component({
    selector: 'jhi-donnee-personnelle-delete-dialog',
    templateUrl: './donnee-personnelle-delete-dialog.component.html'
})
export class DonneePersonnelleDeleteDialogComponent {

    donneePersonnelle: DonneePersonnelle;

    constructor(
        private donneePersonnelleService: DonneePersonnelleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.donneePersonnelleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'donneePersonnelleListModification',
                content: 'Deleted an donneePersonnelle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-donnee-personnelle-delete-popup',
    template: ''
})
export class DonneePersonnelleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private donneePersonnellePopupService: DonneePersonnellePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.donneePersonnellePopupService
                .open(DonneePersonnelleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
