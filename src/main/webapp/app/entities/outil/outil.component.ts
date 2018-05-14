import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Outil } from './outil.model';
import { OutilService } from './outil.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-outil',
    templateUrl: './outil.component.html'
})
export class OutilComponent implements OnInit, OnDestroy {
outils: Outil[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private outilService: OutilService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.outilService.query().subscribe(
            (res: HttpResponse<Outil[]>) => {
                this.outils = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInOutils();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Outil) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInOutils() {
        this.eventSubscriber = this.eventManager.subscribe('outilListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
