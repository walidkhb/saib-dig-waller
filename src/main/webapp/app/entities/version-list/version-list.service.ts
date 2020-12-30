import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IVersionList } from 'app/shared/model/version-list.model';

type EntityResponseType = HttpResponse<IVersionList>;
type EntityArrayResponseType = HttpResponse<IVersionList[]>;

@Injectable({ providedIn: 'root' })
export class VersionListService {
  public resourceUrl = SERVER_API_URL + 'api/version-lists';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/version-lists';

  constructor(protected http: HttpClient) {}

  create(versionList: IVersionList): Observable<EntityResponseType> {
    return this.http.post<IVersionList>(this.resourceUrl, versionList, { observe: 'response' });
  }

  update(versionList: IVersionList): Observable<EntityResponseType> {
    return this.http.put<IVersionList>(this.resourceUrl, versionList, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVersionList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVersionList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVersionList[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
