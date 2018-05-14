import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DonneePersonnelle } from './donnee-personnelle.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DonneePersonnelle>;

@Injectable()
export class DonneePersonnelleService {

    private resourceUrl =  SERVER_API_URL + 'api/donnee-personnelles';

    constructor(private http: HttpClient) { }

    create(donneePersonnelle: DonneePersonnelle): Observable<EntityResponseType> {
        const copy = this.convert(donneePersonnelle);
        return this.http.post<DonneePersonnelle>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(donneePersonnelle: DonneePersonnelle): Observable<EntityResponseType> {
        const copy = this.convert(donneePersonnelle);
        return this.http.put<DonneePersonnelle>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DonneePersonnelle>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DonneePersonnelle[]>> {
        const options = createRequestOption(req);
        return this.http.get<DonneePersonnelle[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DonneePersonnelle[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DonneePersonnelle = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DonneePersonnelle[]>): HttpResponse<DonneePersonnelle[]> {
        const jsonResponse: DonneePersonnelle[] = res.body;
        const body: DonneePersonnelle[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DonneePersonnelle.
     */
    private convertItemFromServer(donneePersonnelle: DonneePersonnelle): DonneePersonnelle {
        const copy: DonneePersonnelle = Object.assign({}, donneePersonnelle);
        return copy;
    }

    /**
     * Convert a DonneePersonnelle to a JSON which can be sent to the server.
     */
    private convert(donneePersonnelle: DonneePersonnelle): DonneePersonnelle {
        const copy: DonneePersonnelle = Object.assign({}, donneePersonnelle);
        return copy;
    }
}
