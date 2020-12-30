import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBankList } from 'app/shared/model/bank-list.model';

type EntityResponseType = HttpResponse<IBankList>;
type EntityArrayResponseType = HttpResponse<IBankList[]>;

@Injectable({ providedIn: 'root' })
export class BankListService {
  public resourceUrl = SERVER_API_URL + 'api/bank-lists';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/bank-lists';

  constructor(protected http: HttpClient) {}

  create(bankList: IBankList): Observable<EntityResponseType> {
    return this.http.post<IBankList>(this.resourceUrl, bankList, { observe: 'response' });
  }

  update(bankList: IBankList): Observable<EntityResponseType> {
    return this.http.put<IBankList>(this.resourceUrl, bankList, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBankList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankList[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
