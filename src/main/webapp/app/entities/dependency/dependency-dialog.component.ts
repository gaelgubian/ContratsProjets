import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Dependency } from './dependency.model';
import { DependencyPopupService } from './dependency-popup.service';
import { DependencyService } from './dependency.service';
import { Application, ApplicationService } from '../application';

@Component({
    selector: 'jhi-dependency-dialog',
    templateUrl: './dependency-dialog.component.html'
})
export class DependencyDialogComponent implements OnInit {

    dependency: Dependency;
    isSaving: boolean;

    applications: Application[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dependencyService: DependencyService,
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
        if (this.dependency.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dependencyService.update(this.dependency));
        } else {
            this.subscribeToSaveResponse(
                this.dependencyService.create(this.dependency));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Dependency>>) {
        result.subscribe((res: HttpResponse<Dependency>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Dependency) {
        this.eventManager.broadcast({ name: 'dependencyListModification', content: 'OK'});
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
    selector: 'jhi-dependency-popup',
    template: ''
})
export class DependencyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dependencyPopupService: DependencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dependencyPopupService
                    .open(DependencyDialogComponent as Component, params['id']);
            } else {
                this.dependencyPopupService
                    .open(DependencyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
