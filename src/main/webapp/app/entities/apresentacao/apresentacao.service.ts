import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApresentacao } from 'app/shared/model/apresentacao.model';

type EntityResponseType = HttpResponse<IApresentacao>;
type EntityArrayResponseType = HttpResponse<IApresentacao[]>;

@Injectable({ providedIn: 'root' })
export class ApresentacaoService {
    public resourceUrl = SERVER_API_URL + 'api/apresentacaos';

    constructor(protected http: HttpClient) {}

    create(apresentacao: IApresentacao): Observable<EntityResponseType> {
        return this.http.post<IApresentacao>(this.resourceUrl, apresentacao, { observe: 'response' });
    }

    update(apresentacao: IApresentacao): Observable<EntityResponseType> {
        return this.http.put<IApresentacao>(this.resourceUrl, apresentacao, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IApresentacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IApresentacao[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
