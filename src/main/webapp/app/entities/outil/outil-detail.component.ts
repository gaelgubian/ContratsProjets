import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Outil } from './outil.model';
import { OutilService } from './outil.service';

@Component({
    selector: 'jhi-outil-detail',
    templateUrl: './outil-detail.component.html'
})
export class OutilDetailComponent implements OnInit, OnDestroy {

    outil: Outil;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private outilService: OutilService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOutils();
    }

    load(id) {
        this.outilService.find(id)
            .subscribe((outilResponse: HttpResponse<Outil>) => {
                this.outil = outilResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOutils() {
        this.eventSubscriber = this.eventManager.subscribe(
            'outilListModification',
            (response) => this.load(this.outil.id)
        );
    }
}
