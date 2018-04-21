import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TransactionType } from './transaction-type.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TransactionType>;

@Injectable()
export class TransactionTypeService {

    private resourceUrl =  SERVER_API_URL + 'api/transaction-types';

    constructor(private http: HttpClient) { }

    create(transactionType: TransactionType): Observable<EntityResponseType> {
        const copy = this.convert(transactionType);
        return this.http.post<TransactionType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(transactionType: TransactionType): Observable<EntityResponseType> {
        const copy = this.convert(transactionType);
        return this.http.put<TransactionType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TransactionType>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TransactionType[]>> {
        const options = createRequestOption(req);
        return this.http.get<TransactionType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TransactionType[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TransactionType = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TransactionType[]>): HttpResponse<TransactionType[]> {
        const jsonResponse: TransactionType[] = res.body;
        const body: TransactionType[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TransactionType.
     */
    private convertItemFromServer(transactionType: TransactionType): TransactionType {
        const copy: TransactionType = Object.assign({}, transactionType);
        return copy;
    }

    /**
     * Convert a TransactionType to a JSON which can be sent to the server.
     */
    private convert(transactionType: TransactionType): TransactionType {
        const copy: TransactionType = Object.assign({}, transactionType);
        return copy;
    }
}
