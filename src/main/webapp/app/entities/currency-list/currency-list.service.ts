import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICurrencyList } from 'app/shared/model/currency-list.model';

type EntityResponseType = HttpResponse<ICurrencyList>;
type EntityArrayResponseType = HttpResponse<ICurrencyList[]>;

@Injectable({ providedIn: 'root' })
export class CurrencyListService {
  public resourceUrl = SERVER_API_URL + 'api/currency-lists';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/currency-lists';

  constructor(protected http: HttpClient) {}

  create(currencyList: ICurrencyList): Observable<EntityResponseType> {
    return this.http.post<ICurrencyList>(this.resourceUrl, currencyList, { observe: 'response' });
  }

  update(currencyList: ICurrencyList): Observable<EntityResponseType> {
    return this.http.put<ICurrencyList>(this.resourceUrl, currencyList, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurrencyList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurrencyList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurrencyList[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
