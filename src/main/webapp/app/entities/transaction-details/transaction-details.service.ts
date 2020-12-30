import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITransactionDetails } from 'app/shared/model/transaction-details.model';

type EntityResponseType = HttpResponse<ITransactionDetails>;
type EntityArrayResponseType = HttpResponse<ITransactionDetails[]>;

@Injectable({ providedIn: 'root' })
export class TransactionDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-details';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/transaction-details';

  constructor(protected http: HttpClient) {}

  create(transactionDetails: ITransactionDetails): Observable<EntityResponseType> {
    return this.http.post<ITransactionDetails>(this.resourceUrl, transactionDetails, { observe: 'response' });
  }

  update(transactionDetails: ITransactionDetails): Observable<EntityResponseType> {
    return this.http.put<ITransactionDetails>(this.resourceUrl, transactionDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
