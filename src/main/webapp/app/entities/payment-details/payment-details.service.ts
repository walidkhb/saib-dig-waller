import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IPaymentDetails } from 'app/shared/model/payment-details.model';

type EntityResponseType = HttpResponse<IPaymentDetails>;
type EntityArrayResponseType = HttpResponse<IPaymentDetails[]>;

@Injectable({ providedIn: 'root' })
export class PaymentDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/payment-details';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/payment-details';

  constructor(protected http: HttpClient) {}

  create(paymentDetails: IPaymentDetails): Observable<EntityResponseType> {
    return this.http.post<IPaymentDetails>(this.resourceUrl, paymentDetails, { observe: 'response' });
  }

  update(paymentDetails: IPaymentDetails): Observable<EntityResponseType> {
    return this.http.put<IPaymentDetails>(this.resourceUrl, paymentDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
