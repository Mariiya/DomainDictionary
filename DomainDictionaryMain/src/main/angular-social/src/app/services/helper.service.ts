import { Injectable } from '@angular/core';
import {MatSnackBar, MatSnackBarConfig} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class HelperService {
  private static _snackBar: MatSnackBar;

  constructor() { }

  static openSnackBar(message: string, action: string) {

    const config = new MatSnackBarConfig();

    config.panelClass = ['snack-bar-error'];
    config.duration = 10000;

    this._snackBar.open(message, action, config);
  }
}
