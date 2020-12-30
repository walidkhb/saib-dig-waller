import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IDestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';

type EntityResponseType = HttpResponse<IDestinationChargeAmount>;
type EntityArrayResponseType = HttpResponse<IDestinationChargeAmount[]>;

@Injectable({ providedIn: 'root' })
export class DestinationChargeAmountService {
  public resourceUrl = SERVER_API_URL + 'api/destination-charge-amounts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/destination-charge-amounts';

  constructor(protected http: HttpClient) {}

  create(destinationChargeAmount: IDestinationChargeAmount): Observable<EntityResponseType> {
    return this.http.post<IDestinationChargeAmount>(this.resourceUrl, destinationChargeAmount, { observe: 'response' });
  }

  update(destinationChargeAmount: IDestinationChargeAmount): Observable<EntityResponseType> {
    return this.http.put<IDestinationChargeAmount>(this.resourceUrl, destinationChargeAmount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDestinationChargeAmount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDestinationChargeAmount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDestinationChargeAmount[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
