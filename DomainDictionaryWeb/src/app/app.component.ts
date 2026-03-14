import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { AuthService } from './core/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <nav class="nav">
      <div class="container">
        <strong>Domain Dictionary</strong>
        <div>
          @if (auth.isLoggedIn()) {
            <a routerLink="/search">Пошук</a>
            <a routerLink="/resources">Мої словники</a>
            <a routerLink="/upload">Завантажити словник</a>
            <a href="#" (click)="auth.logout(); $event.preventDefault()">Вийти</a>
          } @else {
            <a routerLink="/login">Увійти</a>
            <a routerLink="/register">Реєстрація</a>
          }
        </div>
      </div>
    </nav>
    <main class="container" style="padding-top: 24px;">
      <router-outlet />
    </main>
  `
})
export class AppComponent {
  constructor(public auth: AuthService) {}
}
