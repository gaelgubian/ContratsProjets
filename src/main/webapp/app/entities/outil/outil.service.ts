import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Outil } from './outil.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Outil>;

@Injectable()
export class OutilService {

    private resourceUrl =  SERVER_API_URL + 'api/outils';

    constructor(private http: HttpClient) { }

    create(outil: Outil): Observable<EntityResponseType> {
        const copy = this.convert(outil);
        return this.http.post<Outil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(outil: Outil): Observable<EntityResponseType> {
        const copy = this.convert(outil);
        return this.http.put<Outil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Outil>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Outil[]>> {
        const options = createRequestOption(req);
        return this.http.get<Outil[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Outil[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Outil = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Outil[]>): HttpResponse<Outil[]> {
        const jsonResponse: Outil[] = res.body;
        const body: Outil[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Outil.
     */
    private convertItemFromServer(outil: Outil): Outil {
        const copy: Outil = Object.assign({}, outil);
        return copy;
    }

    /**
     * Convert a Outil to a JSON which can be sent to the server.
     */
    private convert(outil: Outil): Outil {
        const copy: Outil = Object.assign({}, outil);
        return copy;
    }
}
