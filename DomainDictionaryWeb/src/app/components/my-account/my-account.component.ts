import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../../services/token-storage.service";
import {HelperService} from "../../services/helper.service";

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss']
})
export class MyAccountComponent implements OnInit {

  id: number =0;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    //@ts-ignore
    this.id = this.route.snapshot.paramMap.get('id')
  }

}
