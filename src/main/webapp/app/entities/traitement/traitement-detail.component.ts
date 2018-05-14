import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Traitement } from './traitement.model';
import { TraitementService } from './traitement.service';

@Component({
    selector: 'jhi-traitement-detail',
    templateUrl: './traitement-detail.component.html'
})
export class TraitementDetailComponent implements OnInit, OnDestroy {

    traitement: Traitement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private traitementService: TraitementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTraitements();
    }

    load(id) {
        this.traitementService.find(id)
            .subscribe((traitementResponse: HttpResponse<Traitement>) => {
                this.traitement = traitementResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTraitements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'traitementListModification',
            (response) => this.load(this.traitement.id)
        );
    }
}
