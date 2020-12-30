import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICustomerPreference } from 'app/shared/model/customer-preference.model';

type EntityResponseType = HttpResponse<ICustomerPreference>;
type EntityArrayResponseType = HttpResponse<ICustomerPreference[]>;

@Injectable({ providedIn: 'root' })
export class CustomerPreferenceService {
  public resourceUrl = SERVER_API_URL + 'api/customer-preferences';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/customer-preferences';

  constructor(protected http: HttpClient) {}

  create(customerPreference: ICustomerPreference): Observable<EntityResponseType> {
    return this.http.post<ICustomerPreference>(this.resourceUrl, customerPreference, { observe: 'response' });
  }

  update(customerPreference: ICustomerPreference): Observable<EntityResponseType> {
    return this.http.put<ICustomerPreference>(this.resourceUrl, customerPreference, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerPreference>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerPreference[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerPreference[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
