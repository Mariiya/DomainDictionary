import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';

import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {Rule} from "../../../model/rule";
import {MatDialog} from "@angular/material/dialog";
import {ResourceBankService} from "../../../services/resource-bank.service";
import {ElectronicDictionary} from "../../../model/electronic-dictionary";
import {MatHorizontalStepper} from "@angular/material/stepper";

@Component({
  selector: 'app-dictionary-parameters',
  templateUrl: './dictionary-parameters.component.html',
  styleUrls: ['./dictionary-parameters.component.css']
})
export class DictionaryParametersComponent implements OnInit {
  classReference =  DictionaryParametersComponent;
  type: Observable<string[]>;
  subtype: Observable<string[]>;

  static formGroup: FormGroup = new FormGroup({
    dname: new FormControl('', [Validators.required]),
    author: new FormControl('', [Validators.required]),
  });

  @Output("dictionaryParam") dictionary = new EventEmitter<ElectronicDictionary>();
  dictionaryInput = new ElectronicDictionary();

  updateDictionaryParams() {
    this.dictionaryInput.name=this.classReference.formGroup.controls.dname.value;
    this.dictionaryInput.author=this.classReference.formGroup.controls.author.value;
    this.dictionary.emit(this.dictionaryInput);
  }

  getNameErrorMessage() {
    if (this.classReference.formGroup.controls.dname.hasError('pattern')) {
      return  'Name should be more than 8 chars and match pattern';
    }

    return 'Name is not correct';
  }

  getAuthorErrorMessage() {
    if (this.classReference.formGroup.controls.author.hasError('pattern')) {
      return  'Author should be more than 8 chars and match pattern';
    }

    return 'Author is not correct';
  }

  constructor(public dialog: MatDialog, public resourceBankService: ResourceBankService) {
    this.type = new Observable<string[]>();
    this.subtype = new Observable<string[]>();
  }

  ngOnInit(): void {
    this.type = this.resourceBankService.getPossibleResourceTypes();
  }

  onTypeSelect() {
    console.log("Selected value " + this.dictionaryInput.type)
    if (this.dictionaryInput.type != undefined) {
      this.subtype = this.resourceBankService.getPossibleResourceSubtypes(this.dictionaryInput.type);
    }
    this.updateDictionaryParams();
  }


}
