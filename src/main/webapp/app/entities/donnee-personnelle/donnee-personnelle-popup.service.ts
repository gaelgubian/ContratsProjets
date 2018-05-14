import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DonneePersonnelle } from './donnee-personnelle.model';
import { DonneePersonnelleService } from './donnee-personnelle.service';

@Injectable()
export class DonneePersonnellePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private donneePersonnelleService: DonneePersonnelleService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.donneePersonnelleService.find(id)
                    .subscribe((donneePersonnelleResponse: HttpResponse<DonneePersonnelle>) => {
                        const donneePersonnelle: DonneePersonnelle = donneePersonnelleResponse.body;
                        this.ngbModalRef = this.donneePersonnelleModalRef(component, donneePersonnelle);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.donneePersonnelleModalRef(component, new DonneePersonnelle());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    donneePersonnelleModalRef(component: Component, donneePersonnelle: DonneePersonnelle): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.donneePersonnelle = donneePersonnelle;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
