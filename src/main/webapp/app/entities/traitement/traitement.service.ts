import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Traitement } from './traitement.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Traitement>;

@Injectable()
export class TraitementService {

    private resourceUrl =  SERVER_API_URL + 'api/traitements';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(traitement: Traitement): Observable<EntityResponseType> {
        const copy = this.convert(traitement);
        return this.http.post<Traitement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(traitement: Traitement): Observable<EntityResponseType> {
        const copy = this.convert(traitement);
        return this.http.put<Traitement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Traitement>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Traitement[]>> {
        const options = createRequestOption(req);
        return this.http.get<Traitement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Traitement[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Traitement = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Traitement[]>): HttpResponse<Traitement[]> {
        const jsonResponse: Traitement[] = res.body;
        const body: Traitement[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Traitement.
     */
    private convertItemFromServer(traitement: Traitement): Traitement {
        const copy: Traitement = Object.assign({}, traitement);
        copy.dateDebut = this.dateUtils
            .convertLocalDateFromServer(traitement.dateDebut);
        copy.dateFin = this.dateUtils
            .convertLocalDateFromServer(traitement.dateFin);
        copy.dateMAJ = this.dateUtils
            .convertLocalDateFromServer(traitement.dateMAJ);
        return copy;
    }

    /**
     * Convert a Traitement to a JSON which can be sent to the server.
     */
    private convert(traitement: Traitement): Traitement {
        const copy: Traitement = Object.assign({}, traitement);
        copy.dateDebut = this.dateUtils
            .convertLocalDateToServer(traitement.dateDebut);
        copy.dateFin = this.dateUtils
            .convertLocalDateToServer(traitement.dateFin);
        copy.dateMAJ = this.dateUtils
            .convertLocalDateToServer(traitement.dateMAJ);
        return copy;
    }
}
