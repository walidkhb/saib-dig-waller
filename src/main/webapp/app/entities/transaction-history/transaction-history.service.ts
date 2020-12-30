import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITransactionHistory } from 'app/shared/model/transaction-history.model';

type EntityResponseType = HttpResponse<ITransactionHistory>;
type EntityArrayResponseType = HttpResponse<ITransactionHistory[]>;

@Injectable({ providedIn: 'root' })
export class TransactionHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-histories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/transaction-histories';

  constructor(protected http: HttpClient) {}

  create(transactionHistory: ITransactionHistory): Observable<EntityResponseType> {
    return this.http.post<ITransactionHistory>(this.resourceUrl, transactionHistory, { observe: 'response' });
  }

  update(transactionHistory: ITransactionHistory): Observable<EntityResponseType> {
    return this.http.put<ITransactionHistory>(this.resourceUrl, transactionHistory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionHistory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionHistory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
