import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBranchList } from 'app/shared/model/branch-list.model';

type EntityResponseType = HttpResponse<IBranchList>;
type EntityArrayResponseType = HttpResponse<IBranchList[]>;

@Injectable({ providedIn: 'root' })
export class BranchListService {
  public resourceUrl = SERVER_API_URL + 'api/branch-lists';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/branch-lists';

  constructor(protected http: HttpClient) {}

  create(branchList: IBranchList): Observable<EntityResponseType> {
    return this.http.post<IBranchList>(this.resourceUrl, branchList, { observe: 'response' });
  }

  update(branchList: IBranchList): Observable<EntityResponseType> {
    return this.http.put<IBranchList>(this.resourceUrl, branchList, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBranchList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBranchList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBranchList[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
