import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders, HttpParams, HttpRequest} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Observable, Subject, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {DictionaryEntry} from "../model/dictionaty-entry";
import {ElectronicDictionary} from "../model/electronic-dictionary";

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

  private url = environment.url;

  saveDomainDictionary(datasourceDE: DictionaryEntry[]): Observable<Blob> {
    return this.http.post(`${this.url}file/save-domain-dictionary`, datasourceDE, {responseType: 'blob'}).pipe(
      catchError((err) => {
        this.openSnackBar('Data not found', 'OK')
        return throwError(err);    //Rethrow it back to component
      })
    );
  }

  createElectronicDictionary(dictionary: ElectronicDictionary, file: File) {
    console.log("create ED start")
    const formData = new FormData();
    formData.append("dictionary", JSON.stringify(dictionary));
    formData.append("file", file);
    return this.http.post<any>(`${this.url}dictionary/create-dictionary`, formData);
  /*  return this.http.post(`${this.url}dictionary/create-dictionary`, formData, {responseType: 'blob'}).pipe(
      catchError((err) => {
        console.log(err)
        console.error(err.message);
        this.openSnackBar('Error during creating new Dictionary', 'OK')
        return throwError(err);    //Rethrow it back to component
      })
    );*/

  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 10000,
    });
  }






}
