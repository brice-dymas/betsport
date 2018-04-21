import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Manage } from './manage.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Manage>;

@Injectable()
export class ManageService {

    private resourceUrl =  SERVER_API_URL + 'api/manages';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(manage: Manage): Observable<EntityResponseType> {
        const copy = this.convert(manage);
        return this.http.post<Manage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(manage: Manage): Observable<EntityResponseType> {
        const copy = this.convert(manage);
        return this.http.put<Manage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Manage>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Manage[]>> {
        const options = createRequestOption(req);
        return this.http.get<Manage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Manage[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Manage = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Manage[]>): HttpResponse<Manage[]> {
        const jsonResponse: Manage[] = res.body;
        const body: Manage[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Manage.
     */
    private convertItemFromServer(manage: Manage): Manage {
        const copy: Manage = Object.assign({}, manage);
        copy.managementDate = this.dateUtils
            .convertDateTimeFromServer(manage.managementDate);
        return copy;
    }

    /**
     * Convert a Manage to a JSON which can be sent to the server.
     */
    private convert(manage: Manage): Manage {
        const copy: Manage = Object.assign({}, manage);

        copy.managementDate = this.dateUtils.toDate(manage.managementDate);
        return copy;
    }
}
