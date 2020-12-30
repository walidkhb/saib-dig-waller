import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBeneficiary } from 'app/shared/model/beneficiary.model';

type EntityResponseType = HttpResponse<IBeneficiary>;
type EntityArrayResponseType = HttpResponse<IBeneficiary[]>;

@Injectable({ providedIn: 'root' })
export class BeneficiaryService {
  public resourceUrl = SERVER_API_URL + 'api/beneficiaries';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/beneficiaries';

  constructor(protected http: HttpClient) {}

  create(beneficiary: IBeneficiary): Observable<EntityResponseType> {
    return this.http.post<IBeneficiary>(this.resourceUrl, beneficiary, { observe: 'response' });
  }

  update(beneficiary: IBeneficiary): Observable<EntityResponseType> {
    return this.http.put<IBeneficiary>(this.resourceUrl, beneficiary, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBeneficiary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBeneficiary[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBeneficiary[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
