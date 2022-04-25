import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './components/home/home.component';
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {NotFoundComponent} from './components/not-found/not-found.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {LoginComponent, RegistrationDialog} from './components/login/login.component';
import {MatGridListModule} from '@angular/material/grid-list';
import {NavigationComponent} from './components/navigation/navigation.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatSortModule} from "@angular/material/sort";
import {MatNativeDateModule, MatRippleModule} from "@angular/material/core";
import {MatMenuModule} from "@angular/material/menu";
import {MatStepperModule} from "@angular/material/stepper";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatSelectModule} from "@angular/material/select";
import {MatDialogModule} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatFormFieldModule} from "@angular/material/form-field";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatCardModule} from '@angular/material/card'
import {MatTreeModule} from "@angular/material/tree";
import {TermsComponent} from './components/search/terms/terms.component';
import {DatePipe} from "@angular/common";
import {SearchMainComponent} from './components/search/search-main/search-main.component';
import {MatPaginatorModule} from "@angular/material/paginator";
import {DataSharedService} from "./services/data-shared.service";
import {AuthInterceptor} from "./security/auth.interceptor";
import {
  CreateElectronicDictionaryComponent
} from './components/create-electronic-dictionary/create-electronic-dictionary.component';
import {FooterComponent} from './components/navigation/footer/footer.component';
import { FileUploadComponent } from './components/create-electronic-dictionary/file-upload/file-upload.component';
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {
  FillParamsComponent,
  ParamsHelperDialog
} from './components/create-electronic-dictionary/fill-params/fill-params.component';
import {MatTabsModule} from "@angular/material/tabs";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import { TestDictionaryComponent } from './components/create-electronic-dictionary/test-dictionary/test-dictionary.component';
import { DictionaryParametersComponent } from './components/create-electronic-dictionary/dictionary-parameters/dictionary-parameters.component';
import { SearchResourcesComponent } from './components/search/search-main/search-resources/search-resources.component';
import { SearchParamsComponent } from './components/search/search-main/search-params/search-params.component';
import {MyAccountComponent} from "./components/my-account/my-account.component";

@NgModule({
  declarations: [
    NavigationComponent,
    AppComponent,
    HomeComponent,
    NotFoundComponent,
    LoginComponent,
    HomeComponent,
    TermsComponent,
    SearchMainComponent,
    MyAccountComponent,
    RegistrationDialog,
    CreateElectronicDictionaryComponent,
    FooterComponent,
    FileUploadComponent,
    FillParamsComponent,
    ParamsHelperDialog,
    TestDictionaryComponent,
    DictionaryParametersComponent,
    SearchResourcesComponent,
    SearchParamsComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    MatListModule,
    MatIconModule,
    MatToolbarModule,
    MatGridListModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
    HttpClientModule,
    FormsModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatStepperModule,
    MatRippleModule,
    FlexLayoutModule,
    MatTreeModule,
    MatPaginatorModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatDialogModule,
    MatExpansionModule,
    MatInputModule,
    MatMenuModule,
    MatSelectModule,
    MatSortModule,
    MatTableModule,
    MatNativeDateModule,
    MatProgressBarModule,
    MatTabsModule,
    MatSidenavModule,
    MatSlideToggleModule
  ],
  providers: [DatePipe, DataSharedService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
