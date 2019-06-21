import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGrupo } from 'app/shared/model/grupo.model';

type EntityResponseType = HttpResponse<IGrupo>;
type EntityArrayResponseType = HttpResponse<IGrupo[]>;

@Injectable({ providedIn: 'root' })
export class GrupoService {
    public resourceUrl = SERVER_API_URL + 'api/grupos';

    constructor(protected http: HttpClient) {}

    create(grupo: IGrupo): Observable<EntityResponseType> {
        return this.http.post<IGrupo>(this.resourceUrl, grupo, { observe: 'response' });
    }

    update(grupo: IGrupo): Observable<EntityResponseType> {
        return this.http.put<IGrupo>(this.resourceUrl, grupo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGrupo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGrupo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
