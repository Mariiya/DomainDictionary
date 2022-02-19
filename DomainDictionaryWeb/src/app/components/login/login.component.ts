import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {AuthService} from "../../services/auth.service";
import {TokenStorageService} from "../../services/token-storage.service";
import {User} from "../../model/user";
import {NavigationComponent} from "../navigation/navigation.component";
import {HelperService} from "../../services/helper.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loading: boolean = false;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  user: User = new User('', '', '','');
  form: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)])
  });

  constructor(
    public router: Router, private route: ActivatedRoute, public dialog: MatDialog,
    private authService: AuthService, private tokenStorage: TokenStorageService,
    public helper:HelperService) {
  }


  redirectPage(): void {
    this.router.navigate(['/home']);
  }

  openRegistration(): void {
    const dialogRef = this.dialog.open(RegistrationDialog, {
      width: 'fit-content',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  onSubmit() {
    this.loading = true;
    this.authService.login(this.user.email, this.user.password).subscribe(
      data => {
        this.loading = false;
        if (data.token != undefined && data.user != undefined) {
          this.tokenStorage.saveToken(data.token);
          this.tokenStorage.saveUser(data.user);
        }
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        NavigationComponent.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.redirectPage();
      },
      err => {
        this.loading = false;
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
        this.helper.openSnackBar("Error during login. Please create account are check your credits.","OK")
      }
    );
  }

}

@Component({
  selector: 'registration-dialog',
  templateUrl: 'registration-dialog.html',
})
export class RegistrationDialog {
  data: User | undefined;
  isExpert: boolean = false;
  form: FormGroup = new FormGroup({
    name: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)])
  });

  constructor(public dialogRef: MatDialogRef<RegistrationDialog>, private authService: AuthService, public helper: HelperService) {
  }

  user: User = new User('', '', '', '');

  notImpl(){
    this.helper.openSnackBar("Not yet implemented",'OK');
  }
  onNoClick(): void {
    this.dialogRef.close();
  }

  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  ngOnInit() {
  }

  onSubmit() {
    if (this.isExpert) {
      this.user.role = "ROLE_EXPERT";
    } else {
      this.user.role = "ROLE_USER";
    }
    if (this.form.valid) {
      this.authService.register(this.user).subscribe(
        data => {
          console.log(data);
          this.isSuccessful = true;
          this.isSignUpFailed = false;
          this.helper.openSnackBar("You have been successfully registered. Please, check your email for confirmation. ", "OK");
          this.dialogRef.close();
          },
        err => {
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
          this.helper.openSnackBar(err.error, "OK");
        }
      );
    }else {
      console.log("NO")
      this.helper.openSnackBar("Please fill all required fields","")
    }
  }
}
