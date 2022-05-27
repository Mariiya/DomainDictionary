import {Component, OnInit, ViewChild} from '@angular/core';
import {MatAccordion} from "@angular/material/expansion";
import {DictionaryEntry} from "../../model/dictionaty-entry";
import {UserService} from "../../services/user.service";
import {TokenStorageService} from "../../services/token-storage.service";
import {Router} from "@angular/router";


export interface Tile {
  cols: number;
  rows: number;
  title: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {
  @ViewChild(MatAccordion) accordion: MatAccordion = new MatAccordion();
  tiles: Tile[] = [
    {title: 'Terms', cols: 2, rows: 5},
    {title: 'Search', cols: 6, rows: 5},
  ];
  isLoggedIn = false;
  results: DictionaryEntry [] = [];
  content: string = '';
  numberOfDE:number=0;
  constructor(private userService: UserService,
              private tokenStorageService: TokenStorageService,
              public router: Router,) {  }

  addNewDictionary() {
    this.router.navigate(['/create-electronic-dictionary']);
  }

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    this.userService.getPublicContent().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

  onResourceChanged(value:number){
    this.numberOfDE=(value/2);
  }
}
