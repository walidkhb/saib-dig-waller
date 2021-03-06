import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICalculationInfoDetails } from 'app/shared/model/calculation-info-details.model';

type EntityResponseType = HttpResponse<ICalculationInfoDetails>;
type EntityArrayResponseType = HttpResponse<ICalculationInfoDetails[]>;

@Injectable({ providedIn: 'root' })
export class CalculationInfoDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/calculation-info-details';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/calculation-info-details';

  constructor(protected http: HttpClient) {}

  create(calculationInfoDetails: ICalculationInfoDetails): Observable<EntityResponseType> {
    return this.http.post<ICalculationInfoDetails>(this.resourceUrl, calculationInfoDetails, { observe: 'response' });
  }

  update(calculationInfoDetails: ICalculationInfoDetails): Observable<EntityResponseType> {
    return this.http.put<ICalculationInfoDetails>(this.resourceUrl, calculationInfoDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICalculationInfoDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICalculationInfoDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICalculationInfoDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
