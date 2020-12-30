import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBeneficiaryBank } from 'app/shared/model/beneficiary-bank.model';

type EntityResponseType = HttpResponse<IBeneficiaryBank>;
type EntityArrayResponseType = HttpResponse<IBeneficiaryBank[]>;

@Injectable({ providedIn: 'root' })
export class BeneficiaryBankService {
  public resourceUrl = SERVER_API_URL + 'api/beneficiary-banks';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/beneficiary-banks';

  constructor(protected http: HttpClient) {}

  create(beneficiaryBank: IBeneficiaryBank): Observable<EntityResponseType> {
    return this.http.post<IBeneficiaryBank>(this.resourceUrl, beneficiaryBank, { observe: 'response' });
  }

  update(beneficiaryBank: IBeneficiaryBank): Observable<EntityResponseType> {
    return this.http.put<IBeneficiaryBank>(this.resourceUrl, beneficiaryBank, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBeneficiaryBank>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBeneficiaryBank[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBeneficiaryBank[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
