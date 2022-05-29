import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../../model/user";
import {UserService} from "../../services/user.service";
import {ResourceBankService} from "../../services/resource-bank.service";

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss']
})
export class MyAccountComponent implements OnInit {

  id: number = 0;
  user: User = new User('', '', '', '');
  numOfDD: number = 0;

  constructor(private route: ActivatedRoute, public service: UserService, public resourceBankService:ResourceBankService) {
  }

  ngOnInit(): void {
    //@ts-ignore
    this.id = this.route.snapshot.paramMap.get('id');
    this.service.getUserById(this.id).subscribe(
      data => {
        this.user = data;
      },
      error => console.log(error)
    );
    this.resourceBankService.geNumberOfDDByUser(this.id).subscribe(
      data => {
        this.numOfDD = data;
      },
      error => console.log(error)
    );
  }


}
