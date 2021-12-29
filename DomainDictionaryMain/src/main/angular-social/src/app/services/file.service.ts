import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {DictionaryEntry} from "../model/dictionaty-entry";

@Injectable({
  providedIn: 'root'
})
export class FileService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/pdf',
      responseType: 'blob',
      Accept: 'application/pdf',
      observe: 'response'
    })
  };

  constructor(private http: HttpClient,
              private _snackBar: MatSnackBar) {
  }

  private url = environment.url + 'file';

  saveDomainDictionary(datasourceDE: DictionaryEntry[]): Observable<Blob> {
   return  this.http.post(`${this.url}/save-domain-dictionary`,   datasourceDE, {responseType: 'blob'}).pipe(
      catchError((err) => {
        console.log(err)
        console.error(err.message);
        this.openSnackBar('Data not found', 'OK')
        return throwError(err);    //Rethrow it back to component
      })
    );
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 10000,
    });
  }
}
