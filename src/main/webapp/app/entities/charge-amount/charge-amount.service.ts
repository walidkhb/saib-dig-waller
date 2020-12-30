import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IChargeAmount } from 'app/shared/model/charge-amount.model';

type EntityResponseType = HttpResponse<IChargeAmount>;
type EntityArrayResponseType = HttpResponse<IChargeAmount[]>;

@Injectable({ providedIn: 'root' })
export class ChargeAmountService {
  public resourceUrl = SERVER_API_URL + 'api/charge-amounts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/charge-amounts';

  constructor(protected http: HttpClient) {}

  create(chargeAmount: IChargeAmount): Observable<EntityResponseType> {
    return this.http.post<IChargeAmount>(this.resourceUrl, chargeAmount, { observe: 'response' });
  }

  update(chargeAmount: IChargeAmount): Observable<EntityResponseType> {
    return this.http.put<IChargeAmount>(this.resourceUrl, chargeAmount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChargeAmount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChargeAmount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChargeAmount[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
