import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IFingerDetails } from 'app/shared/model/finger-details.model';

type EntityResponseType = HttpResponse<IFingerDetails>;
type EntityArrayResponseType = HttpResponse<IFingerDetails[]>;

@Injectable({ providedIn: 'root' })
export class FingerDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/finger-details';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/finger-details';

  constructor(protected http: HttpClient) {}

  create(fingerDetails: IFingerDetails): Observable<EntityResponseType> {
    return this.http.post<IFingerDetails>(this.resourceUrl, fingerDetails, { observe: 'response' });
  }

  update(fingerDetails: IFingerDetails): Observable<EntityResponseType> {
    return this.http.put<IFingerDetails>(this.resourceUrl, fingerDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFingerDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFingerDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFingerDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
