import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DonneePersonnelle } from './donnee-personnelle.model';
import { DonneePersonnelleService } from './donnee-personnelle.service';

@Component({
    selector: 'jhi-donnee-personnelle-detail',
    templateUrl: './donnee-personnelle-detail.component.html'
})
export class DonneePersonnelleDetailComponent implements OnInit, OnDestroy {

    donneePersonnelle: DonneePersonnelle;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private donneePersonnelleService: DonneePersonnelleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDonneePersonnelles();
    }

    load(id) {
        this.donneePersonnelleService.find(id)
            .subscribe((donneePersonnelleResponse: HttpResponse<DonneePersonnelle>) => {
                this.donneePersonnelle = donneePersonnelleResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDonneePersonnelles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'donneePersonnelleListModification',
            (response) => this.load(this.donneePersonnelle.id)
        );
    }
}
