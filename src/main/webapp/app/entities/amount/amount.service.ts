import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAmount } from 'app/shared/model/amount.model';

type EntityResponseType = HttpResponse<IAmount>;
type EntityArrayResponseType = HttpResponse<IAmount[]>;

@Injectable({ providedIn: 'root' })
export class AmountService {
  public resourceUrl = SERVER_API_URL + 'api/amounts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/amounts';

  constructor(protected http: HttpClient) {}

  create(amount: IAmount): Observable<EntityResponseType> {
    return this.http.post<IAmount>(this.resourceUrl, amount, { observe: 'response' });
  }

  update(amount: IAmount): Observable<EntityResponseType> {
    return this.http.put<IAmount>(this.resourceUrl, amount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAmount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAmount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAmount[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
