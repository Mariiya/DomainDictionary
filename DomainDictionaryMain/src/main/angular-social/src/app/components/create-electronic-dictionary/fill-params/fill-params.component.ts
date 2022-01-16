import {Component, OnInit} from '@angular/core';
import {Output, EventEmitter} from '@angular/core';
import {Rule} from "../../../model/rule";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-fill-params',
  templateUrl: './fill-params.component.html',
  styleUrls: ['./fill-params.component.css']
})
export class FillParamsComponent implements OnInit {
  ruleValue: Rule = new Rule();
  @Output() ruleEvent = new EventEmitter<Rule>();
  isCustomArticleSeparator = false;

  addNewRule() {
    this.ruleEvent.emit(this.ruleValue);
  }

  addCustomSeparator(val: boolean) {
      this.isCustomArticleSeparator = val;
  }

  constructor(public dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  openHelpDialog() {
    this.dialog.open(ParamsHelperDialog);
  }
}

@Component({
  selector: 'params-info',
  templateUrl: 'params-info.html',
})
export class ParamsHelperDialog {
}
