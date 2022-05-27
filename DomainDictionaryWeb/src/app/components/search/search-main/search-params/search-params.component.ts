import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-search-params',
  templateUrl: './search-params.component.html',
  styleUrls: ['./search-params.component.css']
})
export class SearchParamsComponent implements OnInit {

  constructor() {  }

  ngOnInit(): void {
   this.initParams();
  }

  @Output()
  languageParam = new EventEmitter<string>();
  @Output()
  isSearchInInternetParam = new EventEmitter<boolean>();
  @Output()
  isDomainAnalyzeParam = new EventEmitter<boolean>();
  @Output()
  isFullTextSearchParam = new EventEmitter<boolean>();
  languageRU: boolean = false;
  languageUA: boolean = true;
  languageEN: boolean = false;

  initParams(){
      this.languageParam.emit('ua');
      this.isFullTextSearchParam.emit(true);
      this.isSearchInInternetParam.emit(true);
      this.isDomainAnalyzeParam.emit(true);
  }

  setFullTextSearchParam(value:boolean){
    this.isFullTextSearchParam.emit(value);
  }

  setDomainAnalyzeParam(value:boolean){
    this.isDomainAnalyzeParam.emit(value);
  }

  setSearchInInternetParam(value:boolean){
    console.log(value)
    this.isSearchInInternetParam.emit(value);
  }

  languageChange(languageName:string, value: boolean){
    this.languageRU = false;
    this.languageUA = false;
    this.languageEN = false;
    if(languageName =='languageRU') {
      this.languageRU = value;
      this.languageParam.emit('ru');
    }
    if(languageName=='languageEN') {
      this.languageEN = value;
      this.languageParam.emit('en');
    }
    if(languageName=='languageUA') {
      this.languageUA = value;
      this.languageParam.emit('ua');
    }
  }
}
