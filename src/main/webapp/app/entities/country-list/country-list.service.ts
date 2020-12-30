import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICountryList } from 'app/shared/model/country-list.model';

type EntityResponseType = HttpResponse<ICountryList>;
type EntityArrayResponseType = HttpResponse<ICountryList[]>;

@Injectable({ providedIn: 'root' })
export class CountryListService {
  public resourceUrl = SERVER_API_URL + 'api/country-lists';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/country-lists';

  constructor(protected http: HttpClient) {}

  create(countryList: ICountryList): Observable<EntityResponseType> {
    return this.http.post<ICountryList>(this.resourceUrl, countryList, { observe: 'response' });
  }

  update(countryList: ICountryList): Observable<EntityResponseType> {
    return this.http.put<ICountryList>(this.resourceUrl, countryList, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICountryList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICountryList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICountryList[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
