import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Dependency } from './dependency.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Dependency>;

@Injectable()
export class DependencyService {

    private resourceUrl =  SERVER_API_URL + 'api/dependencies';

    constructor(private http: HttpClient) { }

    create(dependency: Dependency): Observable<EntityResponseType> {
        const copy = this.convert(dependency);
        return this.http.post<Dependency>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dependency: Dependency): Observable<EntityResponseType> {
        const copy = this.convert(dependency);
        return this.http.put<Dependency>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Dependency>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Dependency[]>> {
        const options = createRequestOption(req);
        return this.http.get<Dependency[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Dependency[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Dependency = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Dependency[]>): HttpResponse<Dependency[]> {
        const jsonResponse: Dependency[] = res.body;
        const body: Dependency[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Dependency.
     */
    private convertItemFromServer(dependency: Dependency): Dependency {
        const copy: Dependency = Object.assign({}, dependency);
        return copy;
    }

    /**
     * Convert a Dependency to a JSON which can be sent to the server.
     */
    private convert(dependency: Dependency): Dependency {
        const copy: Dependency = Object.assign({}, dependency);
        return copy;
    }
}
