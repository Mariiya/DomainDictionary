import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {CreateElectronicDictionaryComponent} from "../create-electronic-dictionary.component";

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {
  @ViewChild('fileInput')
  public fileVariable?: File;

  @Output('electronicDictionary')
  dictionaryFile = new EventEmitter<File>();

  public fileName = "";

  constructor() {
  }

  public onFileChange(event: Event) {
    const reader = new FileReader();
    if (event != null && event.target != null && (event.target as HTMLInputElement).files != null) {
      // @ts-ignore
      this.fileName = (event.target as HTMLInputElement).files[0].name;
      // @ts-ignore
      const [file] = event.target.files;
      reader.readAsDataURL(file);
      this.dictionaryFile.emit(file);
      reader.onload = () => {
        //fgdfg
      };
    }
  }


  createEDClassReference = CreateElectronicDictionaryComponent;
  //FileList
  public remove(): void {
    this.createEDClassReference.formGroup.get('file')?.reset(null);
    this.fileName = "";
  }

  ngOnInit(): void {
  }

}
