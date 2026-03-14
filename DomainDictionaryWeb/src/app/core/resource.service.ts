import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ElectronicDictionary, SaveResourceRequest } from '../shared/models/dictionary';

@Injectable({ providedIn: 'root' })
export class ResourceService {
  private readonly apiUrl = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) {}

  getMyDictionaries(): Observable<ElectronicDictionary[]> {
    return this.http.get<ElectronicDictionary[]>(`${this.apiUrl}/dictionaries`);
  }

  deleteDictionary(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/dictionaries/${id}`);
  }

  saveResource(request: SaveResourceRequest): Observable<Blob> {
    return this.http.post(`${this.apiUrl}/resources/save`, request, { responseType: 'blob' });
  }

  uploadDictionary(file: File, name: string, language: string,
                   articleSeparator: string, termSeparator: string, definitionSeparator: string): Observable<unknown> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('name', name);
    formData.append('language', language);
    formData.append('articleSeparator', articleSeparator);
    formData.append('termSeparator', termSeparator);
    formData.append('definitionSeparator', definitionSeparator);
    return this.http.post(`${this.apiUrl}/resources/upload-dictionary`, formData);
  }
}
