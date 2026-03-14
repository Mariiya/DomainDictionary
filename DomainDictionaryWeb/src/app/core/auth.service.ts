import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { JwtResponse, LoginRequest, SignupRequest } from '../shared/models/user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly TOKEN_KEY = 'dd_token';
  private readonly USER_KEY = 'dd_user';
  private readonly apiUrl = `${environment.apiUrl}/api/auth`;

  private _isLoggedIn = signal(!!this.getToken());

  isLoggedIn = computed(() => this._isLoggedIn());

  constructor(private http: HttpClient, private router: Router) {}

  login(request: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.apiUrl}/signin`, request).pipe(
      tap(response => {
        localStorage.setItem(this.TOKEN_KEY, response.token);
        localStorage.setItem(this.USER_KEY, JSON.stringify(response));
        this._isLoggedIn.set(true);
      })
    );
  }

  signup(request: SignupRequest): Observable<unknown> {
    return this.http.post(`${this.apiUrl}/signup`, request);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this._isLoggedIn.set(false);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getUser(): JwtResponse | null {
    const data = localStorage.getItem(this.USER_KEY);
    return data ? JSON.parse(data) : null;
  }
}
