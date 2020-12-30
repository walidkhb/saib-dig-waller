import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITransfer } from 'app/shared/model/transfer.model';

type EntityResponseType = HttpResponse<ITransfer>;
type EntityArrayResponseType = HttpResponse<ITransfer[]>;

@Injectable({ providedIn: 'root' })
export class TransferService {
  public resourceUrl = SERVER_API_URL + 'api/transfers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/transfers';

  constructor(protected http: HttpClient) {}

  create(transfer: ITransfer): Observable<EntityResponseType> {
    return this.http.post<ITransfer>(this.resourceUrl, transfer, { observe: 'response' });
  }

  update(transfer: ITransfer): Observable<EntityResponseType> {
    return this.http.put<ITransfer>(this.resourceUrl, transfer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransfer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransfer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransfer[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
