import {Component, ViewChild} from '@angular/core';
import {MatAccordion} from "@angular/material/expansion";
import {DictionaryEntry} from "../../model/dictionaty-entry";
import {UserService} from "../../services/user.service";


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

export class HomeComponent {
  @ViewChild(MatAccordion) accordion: MatAccordion = new MatAccordion();
  tiles: Tile[] = [
    {title: 'Terms', cols: 2, rows: 5},
    {title: 'Search', cols: 6, rows:5},
  ];

  results: DictionaryEntry [] = [];
  content: string = '';

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getPublicContent().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }
}
