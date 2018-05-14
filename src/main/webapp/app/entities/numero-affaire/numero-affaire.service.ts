import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { NumeroAffaire } from './numero-affaire.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NumeroAffaire>;

@Injectable()
export class NumeroAffaireService {

    private resourceUrl =  SERVER_API_URL + 'api/numero-affaires';

    constructor(private http: HttpClient) { }

    create(numeroAffaire: NumeroAffaire): Observable<EntityResponseType> {
        const copy = this.convert(numeroAffaire);
        return this.http.post<NumeroAffaire>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(numeroAffaire: NumeroAffaire): Observable<EntityResponseType> {
        const copy = this.convert(numeroAffaire);
        return this.http.put<NumeroAffaire>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NumeroAffaire>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NumeroAffaire[]>> {
        const options = createRequestOption(req);
        return this.http.get<NumeroAffaire[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NumeroAffaire[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NumeroAffaire = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NumeroAffaire[]>): HttpResponse<NumeroAffaire[]> {
        const jsonResponse: NumeroAffaire[] = res.body;
        const body: NumeroAffaire[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NumeroAffaire.
     */
    private convertItemFromServer(numeroAffaire: NumeroAffaire): NumeroAffaire {
        const copy: NumeroAffaire = Object.assign({}, numeroAffaire);
        return copy;
    }

    /**
     * Convert a NumeroAffaire to a JSON which can be sent to the server.
     */
    private convert(numeroAffaire: NumeroAffaire): NumeroAffaire {
        const copy: NumeroAffaire = Object.assign({}, numeroAffaire);
        return copy;
    }
}
