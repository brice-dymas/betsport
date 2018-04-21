import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Period } from './period.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Period>;

@Injectable()
export class PeriodService {

    private resourceUrl =  SERVER_API_URL + 'api/periods';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(period: Period): Observable<EntityResponseType> {
        const copy = this.convert(period);
        return this.http.post<Period>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(period: Period): Observable<EntityResponseType> {
        const copy = this.convert(period);
        return this.http.put<Period>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Period>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Period[]>> {
        const options = createRequestOption(req);
        return this.http.get<Period[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Period[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Period = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Period[]>): HttpResponse<Period[]> {
        const jsonResponse: Period[] = res.body;
        const body: Period[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Period.
     */
    private convertItemFromServer(period: Period): Period {
        const copy: Period = Object.assign({}, period);
        copy.beginingHour = this.dateUtils
            .convertDateTimeFromServer(period.beginingHour);
        copy.endingHour = this.dateUtils
            .convertDateTimeFromServer(period.endingHour);
        return copy;
    }

    /**
     * Convert a Period to a JSON which can be sent to the server.
     */
    private convert(period: Period): Period {
        const copy: Period = Object.assign({}, period);

        copy.beginingHour = this.dateUtils.toDate(period.beginingHour);

        copy.endingHour = this.dateUtils.toDate(period.endingHour);
        return copy;
    }
}
