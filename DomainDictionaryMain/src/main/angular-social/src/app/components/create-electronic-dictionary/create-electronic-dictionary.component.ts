import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {Router} from "@angular/router";
import {Rule} from "../../model/rule";

@Component({
  selector: 'app-create-electronic-dictionary',
  templateUrl: './create-electronic-dictionary.component.html',
  styleUrls: ['./create-electronic-dictionary.component.css'],
  providers: [
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: {displayDefaultIndicatorType: false},
    },
  ],
})
export class CreateElectronicDictionaryComponent implements OnInit {
  isEditable = false;
  static formGroup: FormGroup = new FormGroup({
    file: new FormControl(null, {
      validators: [Validators.required, Validators.nullValidator]
    })
  });
// @ts-ignore
  selectedFiles: File;
  classReference = CreateElectronicDictionaryComponent;

  rule?: Rule;

  onRuleChanged(value: Rule) {
   this.rule = value;
  }

  constructor(public router: Router, private _formBuilder: FormBuilder) {
  }

  ngOnInit() {
  }

  selectFile(event: Event) {

  }

  returnHome() {
    this.router.navigate(['/home']);
  }
}
