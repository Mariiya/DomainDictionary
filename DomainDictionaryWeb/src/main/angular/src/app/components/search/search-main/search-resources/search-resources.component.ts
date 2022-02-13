import {Component, EventEmitter, Injectable, Output} from '@angular/core';
import {ResourceBankService} from "../../../../services/resource-bank.service";
import {FlatTreeControl} from "@angular/cdk/tree";
import {CollectionViewer, DataSource, SelectionChange} from "@angular/cdk/collections";
import {BehaviorSubject, merge, Observable} from "rxjs";
import {map} from "rxjs/operators";
import {SearchResource} from "../../../../model/search-resource";


/**
 * Database for dynamic data. When expanding a node in the tree, the data source will need to fetch
 * the descendants data from the database.
 */
@Injectable({providedIn: 'root'})
export class DynamicDatabase {

  dataMap = new Map<string, Map<string, SearchResource[]>>();
  public Ready: Promise<any>;

  constructor(private resourceBankService: ResourceBankService) {
    this.Ready = new Promise((resolve, reject) => {
      this.resourceBankService.getCatalog().subscribe(
        data => {
          let temp = new Map<string, Map<string, SearchResource[]>>();
          temp = data;
          let typeVar: string, subtypeVar, resourceVar;
          Object.entries(temp).forEach((type) => {
            typeVar = type[0];
            let tempSecondMap = new Map<string, SearchResource[]>();
            Object.entries(type[1]).forEach((subtype) => {
              subtypeVar = subtype[0];
              // @ts-ignore
              tempSecondMap.set(subtypeVar, subtype[1]);
            });
            this.dataMap.set(typeVar, tempSecondMap);
          });
          resolve(true);
        });
    });
  }

  initialData(): DynamicFlatNode[] {
    const nodes: DynamicFlatNode[] = [];
    for (let type of this.dataMap.keys()) {
      let temp = new DynamicFlatNode(type, 0, true, true, undefined);
      if (!nodes.includes(temp))
        nodes.push(temp)
    }
    return nodes;
  }

  getChildren(node:  DynamicFlatNode): DynamicFlatNode[] | undefined {
    const nodes: DynamicFlatNode[] = [];
    if (node.level == 0) {
      // @ts-ignore
      for (let subtypes of this.dataMap.get(node.item)) {
        console.log(subtypes)
        let temp = new DynamicFlatNode(subtypes[0], node.level + 1, true, true, subtypes[1]);
        if (!nodes.includes(temp))
          nodes.push(temp)
      }
    }

    if (node.level == 1) {
      // @ts-ignore
        for (let res of node.content) {
          console.log(res)
          let temp = new DynamicFlatNode(res.name, node.level + 1, false, false, res.id);
          if (!nodes.includes(temp))
            nodes.push(temp)
      }
    }
    return nodes;
  }

  isExpandable(node: string): boolean {
    return this.dataMap.has(node);
  }
}

export class DynamicDataSource implements DataSource<DynamicFlatNode> {
  dataChange = new BehaviorSubject<DynamicFlatNode[]>([]);

  get data(): DynamicFlatNode[] {
    return this.dataChange.value;
  }

  set data(value: DynamicFlatNode[]) {
    this._treeControl.dataNodes = value;
    this.dataChange.next(value);
  }

  constructor(
    private _treeControl: FlatTreeControl<DynamicFlatNode>,
    private _database: DynamicDatabase,
  ) {
  }

  connect(collectionViewer: CollectionViewer): Observable<DynamicFlatNode[]> {
    this._treeControl.expansionModel.changed.subscribe(change => {
      if (
        (change as SelectionChange<DynamicFlatNode>).added ||
        (change as SelectionChange<DynamicFlatNode>).removed
      ) {
        this.handleTreeControl(change as SelectionChange<DynamicFlatNode>);
      }
    });

    return merge(collectionViewer.viewChange, this.dataChange).pipe(map(() => this.data));
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }

  handleTreeControl(change: SelectionChange<DynamicFlatNode>) {
    if (change.added) {
      change.added.forEach(node => this.toggleNode(node, true));
    }
    if (change.removed) {
      change.removed
        .slice()
        .reverse()
        .forEach(node => this.toggleNode(node, false));
    }
  }


  toggleNode(node: DynamicFlatNode, expand: boolean) {
    const children = this._database.getChildren(node);
    const index = this.data.indexOf(node);
    if (!children || index < 0) {
      // If no children, or cannot find the node, no op
      return;
    }

    node.isLoading = true;

    setTimeout(() => {
      if (expand) {
        this.data.splice(index + 1, 0, ...(children));
      } else {
        let count = 0;
        for (
          let i = index + 1;
          i < this.data.length && this.data[i].level > node.level;
          i++, count++
        ) {
        }
        this.data.splice(index + 1, count);
      }

      // notify the change
      this.dataChange.next(this.data);
      node.isLoading = false;
    }, 50);
  }
}

@Component({
  selector: 'app-search-resources',
  templateUrl: './search-resources.component.html',
  styleUrls: ['./search-resources.component.css']
})
export class SearchResourcesComponent {
  @Output('selectedResource')
  resourceId = new EventEmitter <number>();
  selectedResourceName: string = 'none';
  constructor(private database: DynamicDatabase) {
    this.treeControl = new FlatTreeControl<DynamicFlatNode>(this.getLevel, this.isExpandable);
    this.dataSource = new DynamicDataSource(this.treeControl, database);
    this.database.Ready.then(() => {
        this.dataSource.data = this.database.initialData();
      }
    );
  }

  treeControl: FlatTreeControl<DynamicFlatNode>;

  dataSource: DynamicDataSource;

  getLevel = (node: DynamicFlatNode) => node.level;

  isExpandable = (node: DynamicFlatNode) => node.expandable;

  hasChild = (_: number, _nodeData: DynamicFlatNode) => _nodeData.expandable

  logNode(node: DynamicFlatNode) {
    this.selectedResourceName = node.item;
    if(typeof node.content === 'number'){
      console.log('node.log'+ node.content)
      this.resourceId.emit(node.content);}
  }
}

export class DynamicFlatNode {
  constructor(
    public item: string,
    public level = 1,
    public expandable = false,
    public isLoading = false,
    public content?: SearchResource[] | number
  ) {
  }
}
