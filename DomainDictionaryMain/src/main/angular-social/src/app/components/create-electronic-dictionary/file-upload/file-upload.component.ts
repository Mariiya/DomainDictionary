import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FileService} from "../../../services/file.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {CreateElectronicDictionaryComponent} from "../create-electronic-dictionary.component";

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  @ViewChild('fileInput')
  public fileVariable: ElementRef | undefined;

  public fileName = "";
  createEDClassReference = CreateElectronicDictionaryComponent;
  constructor(private fb: FormBuilder, private fileService: FileService) {
  }

  public onFileChange(event: Event) {
    const reader = new FileReader();
    // @ts-ignore
    if (event.target.files && event.target.files.length) {
      // @ts-ignore
      this.fileName = event.target.files[0].name;
      // @ts-ignore
      const [file] = event.target.files;
      reader.readAsDataURL(file);
      this.onSubmit();
      reader.onload = () => {
        //fgdfg
      };
    }
  }

  public onSubmit(): void {
    // @ts-ignore
    this.fileService.upload(this.fileName);
  }

  //FileList
  public fileList$: Observable<string[]> = this.fileService.list();

  public remove(fileName: string): void {
    // @ts-ignore
    this.createEDClassReference.formGroup.get('file')?.reset(null);
    this.fileName = "";
    this.fileService.remove(fileName);
  }

  ngOnInit(): void {
  }

}
