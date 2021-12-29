import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {DataSharedService} from "../../../services/data-shared.service";

@Component({
  selector: 'app-terms',
  templateUrl: './terms.component.html',
  styleUrls: ['./terms.component.css']
})
export class TermsComponent implements OnInit {
  inputs:String[] = []
  loading: boolean = true;

  // @ts-ignore
  subscription: Subscription;

  constructor(private data: DataSharedService) { }

  ngOnInit() {
    var mess = this.getTerms();
    this.subscription = this.data.currentMessage.subscribe((message: string) =>mess= message);
  }

  update(){
    this.inputs.push('');
    this.inputs.pop();
    this.data.changeMessage(this.getTerms())
  }
  addInput() {
    this.inputs.push('');
    this.data.changeMessage(this.getTerms())
  }

  getTerms():string{
    console.log(this.inputs)
    return this.inputs.join('###');
  }

   delete(input: any) {
      this.inputs.splice(input, 1);
     this.data.changeMessage(this.getTerms())
  }

  trackByFn(index: any, item: any) {
    return index;
  }

}
