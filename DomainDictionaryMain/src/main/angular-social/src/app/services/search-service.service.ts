import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {MatSnackBar, MatSnackBarConfig} from "@angular/material/snack-bar";
import {Observable} from "rxjs";
import {DictionaryEntry} from "../model/dictionaty-entry";

@Injectable({
  providedIn: 'root'
})
export class SearchServiceService {

  private url = environment.url;
  private baseURL = this.url + 'search';


  constructor(private httpClient: HttpClient, private _snackBar: MatSnackBar) {
  }

  searchTerms(terms: String[], resourceId: number): Observable<DictionaryEntry[]> | null {
    if (resourceId == -1) {
      this.openSnackBar("Select Search Resource", "OK");
    } else {
      for (var j = 0; j < terms.length; j++) {
        terms[j] = terms[j].toUpperCase();
      }


      const headerDict = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Headers': 'Content-Type',
      }

      const requestOptions = {
        headers: new Headers(headerDict),
      };

      return this.httpClient.get<DictionaryEntry[]>(`${this.baseURL}?terms=${terms}&resourceId=${resourceId}`);
    }
    return null;
  }


  openSnackBar(message: string, action: string) {
    const config = new MatSnackBarConfig();

    config.panelClass = ['snack-bar-error'];
    config.duration = 10000;

    this._snackBar.open(message, action, config);
  }

  handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    error = error.error;
    errorMessage = errorMessage.concat(error.error);

    this.openSnackBar(errorMessage, "OK");
  }

}
