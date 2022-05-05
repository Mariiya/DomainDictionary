import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from "../../environments/environment.prod";
import {User} from "../model/user";

const API_URL = environment.url;

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url = environment.url;

  constructor(private http: HttpClient) {
  }

  getUserById(id: number): Observable<any> {
    return this.http.get(this.url + 'api/auth/' + id);
  }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', {responseType: 'text'});
  }
}
