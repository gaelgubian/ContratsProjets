import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { NumeroAffaire } from './numero-affaire.model';
import { NumeroAffaireService } from './numero-affaire.service';

@Component({
    selector: 'jhi-numero-affaire-detail',
    templateUrl: './numero-affaire-detail.component.html'
})
export class NumeroAffaireDetailComponent implements OnInit, OnDestroy {

    numeroAffaire: NumeroAffaire;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private numeroAffaireService: NumeroAffaireService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNumeroAffaires();
    }

    load(id) {
        this.numeroAffaireService.find(id)
            .subscribe((numeroAffaireResponse: HttpResponse<NumeroAffaire>) => {
                this.numeroAffaire = numeroAffaireResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNumeroAffaires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'numeroAffaireListModification',
            (response) => this.load(this.numeroAffaire.id)
        );
    }
}
