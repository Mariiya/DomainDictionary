import { Component, OnInit } from '@angular/core';
import {ThemePalette} from "@angular/material/core";
import {HelperService} from "../../../../services/helper.service";

@Component({
  selector: 'app-search-params',
  templateUrl: './search-params.component.html',
  styleUrls: ['./search-params.component.css']
})
export class SearchParamsComponent implements OnInit {

  constructor(public helper: HelperService) { }

  ngOnInit(): void {
  }

  allComplete: boolean = false;

  updateAllComplete() {
    this.allComplete = this.task.subtasks != null && this.task.subtasks.every((t: { completed: any; }) => t.completed);
  }

  someComplete(): boolean {
    if (this.task.subtasks == null) {
      return false;
    }
    return this.task.subtasks.filter((t: { completed: any; }) => t.completed).length > 0 && !this.allComplete;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.task.subtasks == null) {
      return;
    }
    this.task.subtasks.forEach((t: { completed: boolean; }) => t.completed = completed);
  }

  notImpl(){
    this.helper.openSnackBar("Not yet implemented","")
  }

  task: Task = {
    name: 'Search settings',
    completed: false,
    color: 'primary',
    subtasks: [
      {name: 'Full text search', completed: false, color: 'primary'},
      {name: 'Analyze Domain', completed: false, color: 'accent'},
      {name: 'Search on Web', completed: false, color: 'warn'}
    ]
  };

  language: Task = {
    name: 'Select language',
    completed: false,
    color: 'primary',
    subtasks: [
      {name: 'Russian', completed: false, color: 'primary'},
      {name: 'Ukrainian', completed: false, color: 'accent'},
      {name: 'English', completed: false, color: 'warn'}
    ]
  };

}

export interface Task {
  name: string;
  completed: boolean;
  color: ThemePalette;
  subtasks?: Task[];
}
