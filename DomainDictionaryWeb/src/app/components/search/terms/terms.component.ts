import {Component, OnInit} from "@angular/core";
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

  constructor(private data: DataSharedService) {
    console.log("constructor ")
    console.log(localStorage.getItem('inputs'));
  }

  ngOnInit() {
    var mess = this.getTerms();
    this.subscription = this.data.currentMessage.subscribe((message: string) =>mess= message);
    console.log("on INit")
    console.log(localStorage.getItem('inputs'));
    if(this.inputs.length==0){
     // this.inputs = localStorage.getItem('inputs').split();
      console.log(localStorage.getItem('inputs'));
    }
  }

  update(){
    this.inputs.push('');
    this.inputs.pop();
    this.data.changeMessage(this.getTerms())
    localStorage.setItem(this.inputs.toString(), 'inputs');
   // const defaultReportData = JSON.parse(localStorage.getItem('inputs'));
   // console.log("after put " + defaultReportData)
    var defaultReportData = {
      'station': 'DR P3',
      'days': 'Mon, Tue, Wed, Thur, Fri',
      'startHour': '06:00',
      'endHour': '18:00',
      'demography': 'Cover: All'
    }

    window.localStorage.setItem('defaultReportData', JSON.stringify(defaultReportData));

    // Returns null

    var itemPref = window.localStorage.getItem('defaultReportData')
//@ts-ignore
    var resultObject = JSON.parse(itemPref);

    console.log(resultObject.station);
  }
  addInput() {
    this.inputs.push('');
    this.data.changeMessage(this.getTerms())
  }

  getTerms():string{
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
