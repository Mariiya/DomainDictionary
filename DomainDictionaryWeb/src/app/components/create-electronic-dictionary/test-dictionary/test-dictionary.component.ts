import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-test-dictionary',
  templateUrl: './test-dictionary.component.html',
  styleUrls: ['./test-dictionary.component.css']
})
export class TestDictionaryComponent implements OnInit {
  @Output() createBtn = new EventEmitter<boolean>();
  @Output() testBtn = new EventEmitter<string>();
  createBtnVal = false;

  constructor() {
  }

  ngOnInit(): void {
  }

  revert() {

  }

  test(term: string) {
    this.testBtn.emit(term);
  }

  submit() {
    this.createBtnVal = true;
    this.createBtn.emit(this.createBtnVal);
  }
}
