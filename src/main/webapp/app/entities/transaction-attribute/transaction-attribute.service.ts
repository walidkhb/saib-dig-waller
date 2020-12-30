import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITransactionAttribute } from 'app/shared/model/transaction-attribute.model';

type EntityResponseType = HttpResponse<ITransactionAttribute>;
type EntityArrayResponseType = HttpResponse<ITransactionAttribute[]>;

@Injectable({ providedIn: 'root' })
export class TransactionAttributeService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-attributes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/transaction-attributes';

  constructor(protected http: HttpClient) {}

  create(transactionAttribute: ITransactionAttribute): Observable<EntityResponseType> {
    return this.http.post<ITransactionAttribute>(this.resourceUrl, transactionAttribute, { observe: 'response' });
  }

  update(transactionAttribute: ITransactionAttribute): Observable<EntityResponseType> {
    return this.http.put<ITransactionAttribute>(this.resourceUrl, transactionAttribute, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionAttribute>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionAttribute[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionAttribute[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
