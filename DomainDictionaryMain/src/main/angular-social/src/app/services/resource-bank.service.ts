import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ResourceBankService {
  private url = environment.url + 'dictionary/';

  constructor(private http: HttpClient) {
  }

  getPossibleResourceSubtypes(type: string): Observable<any> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("type", type);
    return this.http.get(this.url + 'possible-resource-subtypes', {params: queryParams});
  }

  getPossibleResourceTypes(): Observable<any> {
    return this.http.get(this.url + 'possible-resource-types');
  }

  getPossibleRelators(): Observable<any> {
    return this.http.get(this.url + 'possible-relators');
  }

  getSearchResources(): Observable<any> {
    return this.http.get(this.url + 'search-resources');
  }

  getCatalog(): Observable<any> {
    return this.http.get(this.url + 'catalog');
  }


  getPossibleArticleSeparator(): Observable<any> {
    return this.http.get(this.url + 'possible-article-separator');
  }

  geNumberOfResources(): Observable<any> {
    return this.http.get(this.url + 'number-of-resources');
  }


}
