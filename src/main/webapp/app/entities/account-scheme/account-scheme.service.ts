import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAccountScheme } from 'app/shared/model/account-scheme.model';

type EntityResponseType = HttpResponse<IAccountScheme>;
type EntityArrayResponseType = HttpResponse<IAccountScheme[]>;

@Injectable({ providedIn: 'root' })
export class AccountSchemeService {
  public resourceUrl = SERVER_API_URL + 'api/account-schemes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/account-schemes';

  constructor(protected http: HttpClient) {}

  create(accountScheme: IAccountScheme): Observable<EntityResponseType> {
    return this.http.post<IAccountScheme>(this.resourceUrl, accountScheme, { observe: 'response' });
  }

  update(accountScheme: IAccountScheme): Observable<EntityResponseType> {
    return this.http.put<IAccountScheme>(this.resourceUrl, accountScheme, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccountScheme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccountScheme[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccountScheme[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
