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

  getPossibleResourceSybtypes(type: string): Observable<any> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("type", type);
    return this.http.get(this.url + 'possible-resource-sybtypes', {params: queryParams});
  }

  getPossibleResourceTypes(): Observable<any> {
    return this.http.get(this.url + 'possible-resource-types');
  }

  getPossibleRelators(): Observable<any> {
    return this.http.get(this.url + 'possible-relators');
  }

  getPossibleArticleSeparator(): Observable<any> {
    return this.http.get(this.url + 'possible-article-separator');
  }
}
