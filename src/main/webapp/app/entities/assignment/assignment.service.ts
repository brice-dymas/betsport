import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Assignment } from './assignment.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Assignment>;

@Injectable()
export class AssignmentService {

    private resourceUrl =  SERVER_API_URL + 'api/assignments';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(assignment: Assignment): Observable<EntityResponseType> {
        const copy = this.convert(assignment);
        return this.http.post<Assignment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(assignment: Assignment): Observable<EntityResponseType> {
        const copy = this.convert(assignment);
        return this.http.put<Assignment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Assignment>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Assignment[]>> {
        const options = createRequestOption(req);
        return this.http.get<Assignment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Assignment[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Assignment = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Assignment[]>): HttpResponse<Assignment[]> {
        const jsonResponse: Assignment[] = res.body;
        const body: Assignment[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Assignment.
     */
    private convertItemFromServer(assignment: Assignment): Assignment {
        const copy: Assignment = Object.assign({}, assignment);
        copy.assignedDate = this.dateUtils
            .convertLocalDateFromServer(assignment.assignedDate);
        copy.assignedTime = this.dateUtils
            .convertDateTimeFromServer(assignment.assignedTime);
        return copy;
    }

    /**
     * Convert a Assignment to a JSON which can be sent to the server.
     */
    private convert(assignment: Assignment): Assignment {
        const copy: Assignment = Object.assign({}, assignment);
        copy.assignedDate = this.dateUtils
            .convertLocalDateToServer(assignment.assignedDate);

        copy.assignedTime = this.dateUtils.toDate(assignment.assignedTime);
        return copy;
    }
}
