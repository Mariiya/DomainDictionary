import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment.prod";
import {User} from "../model/user";
import {MatSnackBar, MatSnackBarConfig} from "@angular/material/snack-bar";
import { sha256} from 'js-sha256';

const AUTH_API = environment.url+'api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private _snackBar: MatSnackBar) { }

  login(name:string,password:string): Observable<any> {
     password =   sha256(password);
    return this.http.post(AUTH_API + 'signin', {
      nameOrEmail: name,
      password: password
    }, httpOptions).pipe(

    )
  }



handleError(error: any) {
  let errorMessage = '';
  const err = error.error;
  // @ts-ignore
  errorMessage = errorMessage.concat(err.errors);

  this.openSnackBar(errorMessage, "OK");
}

openSnackBar(message: string, action: string) {
  const config = new MatSnackBarConfig();

  config.panelClass = ['snack-bar-error'];
  config.duration = 10000;

  this._snackBar.open(message, action, config);
}

  register(user:User): Observable<any> {
    user.password =   sha256(user.password);
    return this.http.post(AUTH_API + 'signup', {
      name: user.name,
      email: user.email,
      password: user.password,
      role: user.role
    }, httpOptions);
  }
}
