import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {DictionaryEntry} from "../model/dictionaty-entry";
import {HelperService} from "./helper.service";

@Injectable({
  providedIn: 'root'
})
export class SearchServiceService {

  private url = environment.url;
  private baseURL = this.url + 'search';


  constructor(private httpClient: HttpClient, public helper: HelperService) {
  }

  searchTerms(terms: String[], params: Map<string, string>): Observable<DictionaryEntry[]> | null {
    if (terms.length == 0 || terms.length == 1 && terms[0] == '') {
      this.helper.openSnackBar("Terms list is empty", "OK");
      return null;
    }
    let url = this.baseURL + '?terms=' + terms;
    params.forEach(function (value, key) {
      url = url + '&' + key + '=' + value;
    });
    return this.httpClient.get<DictionaryEntry[]>(url);

  }

  handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    error = error.error;
    errorMessage = errorMessage.concat(error.error);

    this.helper.openSnackBar(errorMessage, "OK");
  }

}
