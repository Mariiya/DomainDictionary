import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { SearchResult } from '../shared/models/search';

@Injectable({ providedIn: 'root' })
export class SearchService {
  private readonly apiUrl = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) {}

  search(terms: string[], analyzeEnabled = false, domainContext?: string): Observable<SearchResult[]> {
    let params = new HttpParams()
      .set('terms', terms.join(','))
      .set('analyzeEnabled', analyzeEnabled.toString());
    if (domainContext) {
      params = params.set('domainContext', domainContext);
    }
    return this.http.get<SearchResult[]>(`${this.apiUrl}/search`, { params });
  }

  extractKeywordsFromText(text: string): Observable<string[]> {
    const params = new HttpParams().set('text', text);
    return this.http.get<string[]>(`${this.apiUrl}/text-preliminary/text`, { params });
  }

  extractKeywordsFromFile(file: File): Observable<string[]> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<string[]>(`${this.apiUrl}/text-preliminary/file`, formData);
  }
}
