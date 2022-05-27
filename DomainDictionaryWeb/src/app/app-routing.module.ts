import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {LoginComponent} from "./components/login/login.component";
import {
  CreateElectronicDictionaryComponent
} from "./components/create-electronic-dictionary/create-electronic-dictionary.component";
import {MyAccountComponent} from "./components/my-account/my-account.component";

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent},
  {path: 'create-electronic-dictionary', component: CreateElectronicDictionaryComponent},
  {path: 'my-account/:id', component: MyAccountComponent},
  {path: '**', component: NotFoundComponent},

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
