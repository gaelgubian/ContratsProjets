import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Dependency } from './dependency.model';
import { DependencyPopupService } from './dependency-popup.service';
import { DependencyService } from './dependency.service';

@Component({
    selector: 'jhi-dependency-delete-dialog',
    templateUrl: './dependency-delete-dialog.component.html'
})
export class DependencyDeleteDialogComponent {

    dependency: Dependency;

    constructor(
        private dependencyService: DependencyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dependencyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dependencyListModification',
                content: 'Deleted an dependency'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dependency-delete-popup',
    template: ''
})
export class DependencyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dependencyPopupService: DependencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dependencyPopupService
                .open(DependencyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
