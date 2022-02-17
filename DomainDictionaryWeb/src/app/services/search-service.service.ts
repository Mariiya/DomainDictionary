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


  constructor(private httpClient: HttpClient, public helper:HelperService) {
  }

  searchTerms(terms: String[], resourceId: number): Observable<DictionaryEntry[]> | null {
   console.log(terms.length)
    if(terms.length == 0 || terms.length==1 && terms[0] == ''){
      this.helper.openSnackBar("Terms list is empty", "OK");
     return new Observable<DictionaryEntry[]>();
    }
    if (resourceId == -1) {
      this.helper.openSnackBar("Select Search Resource", "OK");
      return new Observable<DictionaryEntry[]>();
    } else {
      /*for (var j = 0; j < terms.length; j++) {
        terms[j] = terms[j].toUpperCase();
      }*/


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
    return new Observable<DictionaryEntry[]>();
  }

  handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    error = error.error;
    errorMessage = errorMessage.concat(error.error);

   this.helper.openSnackBar(errorMessage, "OK");
  }

}
