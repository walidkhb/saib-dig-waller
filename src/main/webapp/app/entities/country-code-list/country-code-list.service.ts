import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICountryCodeList } from 'app/shared/model/country-code-list.model';

type EntityResponseType = HttpResponse<ICountryCodeList>;
type EntityArrayResponseType = HttpResponse<ICountryCodeList[]>;

@Injectable({ providedIn: 'root' })
export class CountryCodeListService {
  public resourceUrl = SERVER_API_URL + 'api/country-code-lists';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/country-code-lists';

  constructor(protected http: HttpClient) {}

  create(countryCodeList: ICountryCodeList): Observable<EntityResponseType> {
    return this.http.post<ICountryCodeList>(this.resourceUrl, countryCodeList, { observe: 'response' });
  }

  update(countryCodeList: ICountryCodeList): Observable<EntityResponseType> {
    return this.http.put<ICountryCodeList>(this.resourceUrl, countryCodeList, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICountryCodeList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICountryCodeList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICountryCodeList[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
