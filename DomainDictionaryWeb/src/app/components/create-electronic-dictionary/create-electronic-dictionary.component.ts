import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {Router} from "@angular/router";
import {Rule} from "../../model/rule";
import {ElectronicDictionary} from "../../model/electronic-dictionary";
import {FileService} from "../../services/file.service";
import {MatHorizontalStepper} from "@angular/material/stepper";
import {HelperService} from "../../services/helper.service";
import {DictionaryParametersComponent} from "./dictionary-parameters/dictionary-parameters.component";
import {FillParamsComponent} from "./fill-params/fill-params.component";

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
  loading: boolean = false;
  isEditable = false;
  static formGroup: FormGroup = new FormGroup({
    file: new FormControl(null, {
      validators: [Validators.required, Validators.nullValidator]
    })
  });

  dictionary: ElectronicDictionary = new ElectronicDictionary();
// @ts-ignore
  selectedFile: File;
  classReference = CreateElectronicDictionaryComponent;

  onDictionaryParamsChanged(dictionary: ElectronicDictionary) {
    this.dictionary.type = dictionary.type;
    this.dictionary.subtype = dictionary.subtype;
    this.dictionary.name = dictionary.name;
    this.dictionary.author = dictionary.author;
  }

  onRuleChanged(value: Rule) {
    this.dictionary.rule = value;
  }

  onFileChanged(file: File) {
    this.selectedFile = file;
  }

  create() {
    this.loading = true;
    if (this.isDictionaryValid()) {
      this.fileService.createElectronicDictionary(this.dictionary, this.selectedFile).subscribe(
        (res) => {
          if(res == false || res == null){
            this.helper.openSnackBar("Error during creating dictionary", "OK")
            this.loading = false;
          }else {
            this.helper.openSnackBar("Dictionary created", "OK")
            this.loading = false;
            this.returnHome();
          }
        },
        (err) => {
          this.helper.openSnackBar("Error during creating dictionary", "OK")
          this.loading = false;
        }
      );
    }
  }

  isDictionaryValid() {
    return this.dictionary != undefined
      && this.dictionary.rule != undefined
      && this.selectedFile != undefined
  }

  testDictionary(term: string) {
   this.helper.openSnackBar("Not yet implemented",'OK');
  }

  constructor(public router: Router, private _formBuilder: FormBuilder,
              public fileService: FileService, public helper: HelperService) {
  }

  ngOnInit() {
  }

  returnHome() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/home']);
  }

  @ViewChild('stepper') stepper?: MatHorizontalStepper;

  onFileFilled() {
    if (this.stepper != undefined && this.selectedFile != undefined)
      this.stepper.next();
    else this.helper.openSnackBar("Please upload dictionary file", "OK")
  }

  dictionaryInfoReference = DictionaryParametersComponent;

  onDictionaryInfoFilled() {
    if (this.stepper != undefined && this.dictionaryInfoReference.formGroup.valid)
      this.stepper.next();
  }
  dictionaryParametersReference = FillParamsComponent;

  onDictionaryParamsFilled() {
    if (this.stepper != undefined && this.dictionaryParametersReference.formGroup.valid)
      this.stepper.next();
  }
}
