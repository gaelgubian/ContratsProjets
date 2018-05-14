import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Dependency } from './dependency.model';
import { DependencyService } from './dependency.service';

@Component({
    selector: 'jhi-dependency-detail',
    templateUrl: './dependency-detail.component.html'
})
export class DependencyDetailComponent implements OnInit, OnDestroy {

    dependency: Dependency;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dependencyService: DependencyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDependencies();
    }

    load(id) {
        this.dependencyService.find(id)
            .subscribe((dependencyResponse: HttpResponse<Dependency>) => {
                this.dependency = dependencyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDependencies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dependencyListModification',
            (response) => this.load(this.dependency.id)
        );
    }
}
