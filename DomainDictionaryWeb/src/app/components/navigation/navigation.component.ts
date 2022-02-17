import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../../services/token-storage.service";
import {HelperService} from "../../services/helper.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  title = 'dd';
  // @ts-ignore
  private roles: string[];
 static isLoggedIn = false;
  public classReference = NavigationComponent;
  username: string = '';

  constructor(private tokenStorageService: TokenStorageService, public router: Router,
              private helper: HelperService) {
  }

  goHome(){
    this.router.navigate(['/home'])
  }
  ngOnInit() {
    NavigationComponent.isLoggedIn = !!this.tokenStorageService.getToken();
    if (NavigationComponent.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      this.username = user.username;
    }
  }

  logout() {
    this.tokenStorageService.signOut();
    this.router.navigate(['/login']).then(r => window.location.reload());
  }

  notImpl(){
    this.helper.openSnackBar("Not yet implemented",'OK');
  }
}
