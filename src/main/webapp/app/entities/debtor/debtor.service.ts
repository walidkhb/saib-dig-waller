import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IDebtor } from 'app/shared/model/debtor.model';

type EntityResponseType = HttpResponse<IDebtor>;
type EntityArrayResponseType = HttpResponse<IDebtor[]>;

@Injectable({ providedIn: 'root' })
export class DebtorService {
  public resourceUrl = SERVER_API_URL + 'api/debtors';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/debtors';

  constructor(protected http: HttpClient) {}

  create(debtor: IDebtor): Observable<EntityResponseType> {
    return this.http.post<IDebtor>(this.resourceUrl, debtor, { observe: 'response' });
  }

  update(debtor: IDebtor): Observable<EntityResponseType> {
    return this.http.put<IDebtor>(this.resourceUrl, debtor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDebtor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDebtor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDebtor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
