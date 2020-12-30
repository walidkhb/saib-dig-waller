import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICalculationInfo } from 'app/shared/model/calculation-info.model';

type EntityResponseType = HttpResponse<ICalculationInfo>;
type EntityArrayResponseType = HttpResponse<ICalculationInfo[]>;

@Injectable({ providedIn: 'root' })
export class CalculationInfoService {
  public resourceUrl = SERVER_API_URL + 'api/calculation-infos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/calculation-infos';

  constructor(protected http: HttpClient) {}

  create(calculationInfo: ICalculationInfo): Observable<EntityResponseType> {
    return this.http.post<ICalculationInfo>(this.resourceUrl, calculationInfo, { observe: 'response' });
  }

  update(calculationInfo: ICalculationInfo): Observable<EntityResponseType> {
    return this.http.put<ICalculationInfo>(this.resourceUrl, calculationInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICalculationInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICalculationInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICalculationInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
