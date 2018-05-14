import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Outil } from './outil.model';
import { OutilPopupService } from './outil-popup.service';
import { OutilService } from './outil.service';

@Component({
    selector: 'jhi-outil-delete-dialog',
    templateUrl: './outil-delete-dialog.component.html'
})
export class OutilDeleteDialogComponent {

    outil: Outil;

    constructor(
        private outilService: OutilService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.outilService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'outilListModification',
                content: 'Deleted an outil'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-outil-delete-popup',
    template: ''
})
export class OutilDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private outilPopupService: OutilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.outilPopupService
                .open(OutilDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
