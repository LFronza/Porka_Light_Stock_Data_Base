import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISaida } from 'app/shared/model/saida.model';

type EntityResponseType = HttpResponse<ISaida>;
type EntityArrayResponseType = HttpResponse<ISaida[]>;

@Injectable({ providedIn: 'root' })
export class SaidaService {
    public resourceUrl = SERVER_API_URL + 'api/saidas';

    constructor(protected http: HttpClient) {}

    create(saida: ISaida): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(saida);
        return this.http
            .post<ISaida>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(saida: ISaida): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(saida);
        return this.http
            .put<ISaida>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISaida>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISaida[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(saida: ISaida): ISaida {
        const copy: ISaida = Object.assign({}, saida, {
            dtSaida: saida.dtSaida != null && saida.dtSaida.isValid() ? saida.dtSaida.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dtSaida = res.body.dtSaida != null ? moment(res.body.dtSaida) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((saida: ISaida) => {
                saida.dtSaida = saida.dtSaida != null ? moment(saida.dtSaida) : null;
            });
        }
        return res;
    }
}
