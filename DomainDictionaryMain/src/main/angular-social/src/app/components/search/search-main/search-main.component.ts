import {Component, OnInit, ViewChild} from '@angular/core';
import {FlatTreeControl} from "@angular/cdk/tree";
import {MatTreeFlatDataSource, MatTreeFlattener} from "@angular/material/tree";
import {ThemePalette} from "@angular/material/core";
import {SearchServiceService} from "../../../services/search-service.service";
import {DictionaryEntry} from "../../../model/dictionaty-entry";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Subscription} from "rxjs";
import {DataSharedService} from "../../../services/data-shared.service";
import {ActivatedRoute} from "@angular/router";
import {FileService} from "../../../services/file.service";

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
              private reportService: FileService) {
    this.dataSource.data = TREE_DATA;
  }

  termsUnsplited: string = ' ';
  // @ts-ignore
  subscription: Subscription;
  loading: boolean = false;
  selectedResource: string = 'none';

  ngOnInit() {
    this.datasourceDE.paginator = this.paginator;
    if(this.datasourceDE.data.length==0){
      let de = new DictionaryEntry(0, "....", ["..........."]);
      this.datasourceDE.data.push(de);
    }
  }

  getTerms(): string[] {
    this.subscription = this.data.currentMessage.subscribe(message => this.termsUnsplited = message);
    return this.termsUnsplited.split('###');
  }

  task: Task = {
    name: 'Search settings',
    completed: false,
    color: 'primary',
    subtasks: [
      {name: 'Full text search', completed: false, color: 'primary'},
      {name: 'Analyze Domain', completed: false, color: 'accent'},
      {name: 'Search on Web', completed: false, color: 'warn'}
    ]
  };

  language: Task = {
    name: 'Select language',
    completed: false,
    color: 'primary',
    subtasks: [
      {name: 'Russian', completed: false, color: 'primary'},
      {name: 'Ukrainian', completed: false, color: 'accent'},
      {name: 'English', completed: false, color: 'warn'}
    ]
  };


  search() {
    const t = this.getTerms();
    if (this.selectedResource == 'none') {
      // @ts-ignore
      this.service.searchTerms(t, -1);

    } else {
      this.loading = true;
      // @ts-ignore
      this.service.searchTerms(t, 1)
        .subscribe(data => {
            console.log("data" + data);
            this.datasourceDE.data = data;
            this.loading = false;
          },
          error => {
            console.log('ERROR ' + error.message);
            this.loading = false;
          });
    }
  }

  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;

  logNode(node: any) {
    this.selectedResource = node.name;
  }

  allComplete: boolean = false;

  updateAllComplete() {
    this.allComplete = this.task.subtasks != null && this.task.subtasks.every(t => t.completed);
  }

  someComplete(): boolean {
    if (this.task.subtasks == null) {
      return false;
    }
    return this.task.subtasks.filter(t => t.completed).length > 0 && !this.allComplete;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.task.subtasks == null) {
      return;
    }
    this.task.subtasks.forEach(t => t.completed = completed);
  }

  private _transformer = (node: ResourceNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
    };
  }

  treeControl = new FlatTreeControl<ExampleFlatNode>(
    node => node.level, node => node.expandable);

  treeFlattener = new MatTreeFlattener(
    this._transformer, node => node.level, node => node.expandable, node => node.children);
  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

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
}


export interface Task {
  name: string;
  completed: boolean;
  color: ThemePalette;
  subtasks?: Task[];
}

interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

interface ResourceNode {
  name: string;
  children?: ResourceNode[];
}

const TREE_DATA: ResourceNode[] = [
  {
    name: 'Web-resource',
    children: [
      {name: 'Wikipedia'},
      {name: 'Gufo'}
    ]
  }, {
    name: 'Dictionaries',
    children: [
      {
        name: 'Languages',
        children: [
          {name: 'Efremov (ru)'},
          {name: 'English Oxford'},
        ]
      }, {
        name: 'Narrow Domain',
        children: [
          {name: 'Chemistry'},
          {name: 'Law'},
          {name: 'Geography'},
        ]
      },
    ]
  },
];

export interface SearchResults {
  term: string;
  number: number;
  definition: string;
}

