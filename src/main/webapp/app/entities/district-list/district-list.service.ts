import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IDistrictList } from 'app/shared/model/district-list.model';

type EntityResponseType = HttpResponse<IDistrictList>;
type EntityArrayResponseType = HttpResponse<IDistrictList[]>;

@Injectable({ providedIn: 'root' })
export class DistrictListService {
  public resourceUrl = SERVER_API_URL + 'api/district-lists';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/district-lists';

  constructor(protected http: HttpClient) {}

  create(districtList: IDistrictList): Observable<EntityResponseType> {
    return this.http.post<IDistrictList>(this.resourceUrl, districtList, { observe: 'response' });
  }

  update(districtList: IDistrictList): Observable<EntityResponseType> {
    return this.http.put<IDistrictList>(this.resourceUrl, districtList, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDistrictList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDistrictList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDistrictList[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
