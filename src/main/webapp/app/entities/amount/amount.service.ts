import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Amount } from './amount.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Amount>;

@Injectable()
export class AmountService {

    private resourceUrl =  SERVER_API_URL + 'api/amounts';

    constructor(private http: HttpClient) { }

    create(amount: Amount): Observable<EntityResponseType> {
        const copy = this.convert(amount);
        return this.http.post<Amount>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(amount: Amount): Observable<EntityResponseType> {
        const copy = this.convert(amount);
        return this.http.put<Amount>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Amount>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Amount[]>> {
        const options = createRequestOption(req);
        return this.http.get<Amount[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Amount[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Amount = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Amount[]>): HttpResponse<Amount[]> {
        const jsonResponse: Amount[] = res.body;
        const body: Amount[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Amount.
     */
    private convertItemFromServer(amount: Amount): Amount {
        const copy: Amount = Object.assign({}, amount);
        return copy;
    }

    /**
     * Convert a Amount to a JSON which can be sent to the server.
     */
    private convert(amount: Amount): Amount {
        const copy: Amount = Object.assign({}, amount);
        return copy;
    }
}
