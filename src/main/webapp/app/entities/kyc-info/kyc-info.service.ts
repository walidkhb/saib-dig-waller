import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IKYCInfo } from 'app/shared/model/kyc-info.model';

type EntityResponseType = HttpResponse<IKYCInfo>;
type EntityArrayResponseType = HttpResponse<IKYCInfo[]>;

@Injectable({ providedIn: 'root' })
export class KYCInfoService {
  public resourceUrl = SERVER_API_URL + 'api/kyc-infos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/kyc-infos';

  constructor(protected http: HttpClient) {}

  create(kYCInfo: IKYCInfo): Observable<EntityResponseType> {
    return this.http.post<IKYCInfo>(this.resourceUrl, kYCInfo, { observe: 'response' });
  }

  update(kYCInfo: IKYCInfo): Observable<EntityResponseType> {
    return this.http.put<IKYCInfo>(this.resourceUrl, kYCInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKYCInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKYCInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKYCInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
