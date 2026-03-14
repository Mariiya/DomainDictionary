import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-results',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="card">
      <h2>Результати пошуку</h2>
      <p>Перейдіть на сторінку <a routerLink="/search">Пошук</a> для початку роботи.</p>
    </div>
  `
})
export class ResultsComponent {}
