import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {SearchServiceService} from "../../../services/search-service.service";
import {DictionaryEntry} from "../../../model/dictionaty-entry";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Observable, Subscription} from "rxjs";
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
  @Output()
  numberOfDE = new EventEmitter<number>();
  searchParams: Map<string, any> = new Map<string, any>();
  displayedColumns: string[] = ['term', 'definition'];

  // @ts-ignore
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  datasourceDE = new MatTableDataSource<DictionaryEntry>([]);

  constructor(private service: SearchServiceService,
              private data: DataSharedService,
              private route: ActivatedRoute,
              private reportService: FileService,
              private helper: HelperService) {
  }

  termsUnsplited: string = ' ';
  // @ts-ignore
  subscription: Subscription;
  loading: boolean = false;
  isEditable = false;

  ngOnInit() {
    this.datasourceDE.paginator = this.paginator;
    if (this.datasourceDE.data.length == 0) {
      let de = new DictionaryEntry(0, "....", ["..........."]);
      this.datasourceDE.data.push(de);
    }
    this.searchParams.set('language', 'ru');
    this.searchParams.set('isDomainAnalyze', true);
    this.searchParams.set('isFullTextSearch', true);
    this.searchParams.set('isSearchInInternet', true);

  }

  getTerms(): string[] {
    this.subscription = this.data.currentMessage.subscribe(message => this.termsUnsplited = message);
    var temp = this.termsUnsplited.split('###');
    return temp.filter(t => {
      return !(t == undefined || t.trim().length == 0);
    })
  }

  selectedResource: number = -1;

  search() {
    const t = this.getTerms();
    if (t.length == 0 || t.length == 1 && t[0] == '') {
      this.helper.openSnackBar("Terms list is empty", "OK");
      return;
    } else {
      if (this.selectedResource == -1) {
        this.helper.openSnackBar("Select Search Resource", "OK");
        return;
      } else {
        this.loading = true;
        this.searchParams.set('resourceId', String(this.selectedResource))
        // @ts-ignore
        this.service.searchTerms(t, this.searchParams)
          .subscribe(data => {
              if (data == null || data.length == 0) {
                this.helper.openSnackBar("Nothing found", "OK");
                this.loading = false;
              } else {
                this.datasourceDE.data = data;
                this.numberOfDE.emit(data.length);
              }
              this.loading = false;
            },
            error => {
              this.helper.openSnackBar("Nothing found", "OK");
              this.loading = false;
            });
      }
    }
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

  onResourceChanged(value: number) {
    this.selectedResource = value;
  }

  deleteDefinition(dictionaryEntry: DictionaryEntry, definition: string) {
    this.datasourceDE.data.forEach(function (de) {
      if (de.id == dictionaryEntry.id) {
        if (de.definition != null) {
          de.definition.forEach(function (def, index) {
            if (def == definition && de != null && de.definition != null) {
              de.definition.splice(index, 1);
            }
          })
        }
      }
    });
  }

  edit(enable: boolean) {
    this.isEditable = enable;
  }

  setLanguage(value: string) {
    this.searchParams.set('language', value);
  }

  setDomainAnalyzeParam(value: boolean) {
    this.searchParams.set('isDomainAnalyze', value);
  }

  setFullTextSearchParam(value: boolean) {
    this.searchParams.set('isFullTextSearch', value);
  }

  setSearchInInternetParam(value: boolean) {
    this.searchParams.set('isSearchInInternet', value);
  }

}


