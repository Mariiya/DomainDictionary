import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {SearchServiceService} from "../../../services/search-service.service";
import {DictionaryEntry} from "../../../model/dictionaty-entry";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Subscription} from "rxjs";
import {DataSharedService} from "../../../services/data-shared.service";
import {ActivatedRoute} from "@angular/router";
import {FileService} from "../../../services/file.service";
import {HelperService} from "../../../services/helper.service";

@Component({
  selector: 'app-search-main',
  templateUrl: './search-main.component.html',
  styleUrls: ['./search-main.component.css']
})
export class SearchMainComponent implements OnInit {

  displayedColumns: string[] = ['term', 'definition'];

  // @ts-ignore
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  datasourceDE = new MatTableDataSource<DictionaryEntry>([]);

  constructor(private service: SearchServiceService,
              private data: DataSharedService,
              private route: ActivatedRoute,
              private reportService: FileService,
              private helper: HelperService){
  }

  termsUnsplited: string = ' ';
  // @ts-ignore
  subscription: Subscription;
  loading: boolean = false;
  selectedResource: number = -1;


  ngOnInit() {
    this.datasourceDE.paginator = this.paginator;
    if (this.datasourceDE.data.length == 0) {
      let de = new DictionaryEntry(0, "....", ["..........."]);
      this.datasourceDE.data.push(de);
    }
  }

  getTerms(): string[] {
    this.subscription = this.data.currentMessage.subscribe(message => this.termsUnsplited = message);
    return this.termsUnsplited.split('###');
  }

  search() {
    const t = this.getTerms();
      this.loading = true;
      // @ts-ignore
      this.service.searchTerms(t, this.selectedResource)
        .subscribe(data => {
          if(data == null || data.length==0){
            this.helper.openSnackBar("Nothing found", "OK");
            this.loading = false;
            console.log("data " + data);
          }else {
            this.datasourceDE.data = data;
            console.log("data " + this.datasourceDE.data);
          }
            this.loading = false;
          },
          error => {
            this.helper.openSnackBar("Nothing found", "OK");
            this.loading = false;
          });
  }


  saveDomainDictionary() {
    this.reportService.saveDomainDictionary(this.datasourceDE.data)
      .subscribe(blob => this.subscribeToGetPdf(blob),
        error => {
          console.log(error)
        });
  }

  private blob: Blob | undefined;

  subscribeToGetPdf(data: Blob) {
    this.blob = new Blob([data], {type: 'application/pdf'});
    var downloadURL = window.URL.createObjectURL(data);
    var link = document.createElement('a');
    link.href = downloadURL;
    link.download = "domainDictionary.pdf";
    link.click();
  }

  onResourceChanged(value: number){
      this.selectedResource = value;
      console.log('selectedResource ' + this.selectedResource)
    }

}

export interface SearchResults {
  term: string;
  number: number;
  definition: string;
}

