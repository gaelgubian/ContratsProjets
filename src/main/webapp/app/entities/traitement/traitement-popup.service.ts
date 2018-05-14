import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Traitement } from './traitement.model';
import { TraitementService } from './traitement.service';

@Injectable()
export class TraitementPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private traitementService: TraitementService

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
                this.traitementService.find(id)
                    .subscribe((traitementResponse: HttpResponse<Traitement>) => {
                        const traitement: Traitement = traitementResponse.body;
                        if (traitement.dateDebut) {
                            traitement.dateDebut = {
                                year: traitement.dateDebut.getFullYear(),
                                month: traitement.dateDebut.getMonth() + 1,
                                day: traitement.dateDebut.getDate()
                            };
                        }
                        if (traitement.dateFin) {
                            traitement.dateFin = {
                                year: traitement.dateFin.getFullYear(),
                                month: traitement.dateFin.getMonth() + 1,
                                day: traitement.dateFin.getDate()
                            };
                        }
                        if (traitement.dateMAJ) {
                            traitement.dateMAJ = {
                                year: traitement.dateMAJ.getFullYear(),
                                month: traitement.dateMAJ.getMonth() + 1,
                                day: traitement.dateMAJ.getDate()
                            };
                        }
                        this.ngbModalRef = this.traitementModalRef(component, traitement);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.traitementModalRef(component, new Traitement());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    traitementModalRef(component: Component, traitement: Traitement): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.traitement = traitement;
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
