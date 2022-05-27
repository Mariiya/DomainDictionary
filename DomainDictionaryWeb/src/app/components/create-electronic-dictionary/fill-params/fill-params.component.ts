import {Component, OnInit} from '@angular/core';
import {Output, EventEmitter} from '@angular/core';
import {Rule} from "../../../model/rule";
import {MatDialog} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {ResourceBankService} from "../../../services/resource-bank.service";

@Component({
  selector: 'app-fill-params',
  templateUrl: './fill-params.component.html',
  styleUrls: ['./fill-params.component.css']
})
export class FillParamsComponent implements OnInit {
  reference = FillParamsComponent;
  @Output() ruleEvent = new EventEmitter<Rule>();

  static formGroup: FormGroup = new FormGroup({
    articleSeparator: new FormControl('', Validators.required),
    termSeparator: new FormControl('', [Validators.required, Validators.max(10)]),
    articleSeparatorInput: new FormControl('', Validators.max(10)),
    definitionSeparatorInput: new FormControl('', Validators.max(10)),
    definitionSeparator: new FormControl('', Validators.required),
    stylisticZone: new FormControl('')
  });

  getErrorMessage(): string {
    return 'incorrect';
  }

  ruleValue: Rule = new Rule();

  isCustomArticleSeparator = true;
  isCustomDefinitionSeparator = true;

  relators: Observable<string[]>;
  articleSeparators: Observable<string[]>;

  addNewRule() {
    // @ts-ignore
    this.ruleValue.articleSeparator = this.reference.formGroup.get('articleSeparator').value;
    if (this.isCustomArticleSeparator) {

      this.ruleValue.articleSeparator = this.reference.formGroup.controls.articleSeparatorInput.value;
      console.log("YESq " +this.reference.formGroup.controls.articleSeparatorInput.value)
      console.log("yes3 "+ this.reference.formGroup.controls.articleSeparatorInput)
      console.log("YES2 " + this.ruleValue.articleSeparator)
    } else {
      this.ruleValue.articleSeparator = this.reference.formGroup.controls.articleSeparator.value;
    }

    // @ts-ignore
    this.ruleValue.definitionSeparator = this.reference.formGroup.get('definitionSeparator').value;
    if (this.isCustomDefinitionSeparator) {
      this.ruleValue.definitionSeparator = this.reference.formGroup.controls.definitionSeparatorInput.value;
    } else {
      this.ruleValue.definitionSeparator = this.reference.formGroup.controls.definitionSeparator.value;
    }

    // @ts-ignore
    this.ruleValue.termSeparator = this.reference.formGroup.get('termSeparator').value;
    // @ts-ignore
    this.ruleValue.stylisticZone = this.reference.formGroup.get('stylisticZone').value;
    this.ruleEvent.emit(this.ruleValue);

  }

  addCustomSeparator(val: boolean) {
    console.log("add custom " + val)
    if (val) {
      this.reference.formGroup.controls.articleSeparatorInput.value.remote;
    }
    this.isCustomArticleSeparator = val;
    console.log(" this.articleSeparatorInput " + this.reference.formGroup.controls.articleSeparatorInput.value)
  }

  constructor(public dialog: MatDialog, public resourcesService: ResourceBankService) {
    this.relators = this.resourcesService.getPossibleRelators();
    this.articleSeparators = this.resourcesService.getPossibleArticleSeparator();
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
