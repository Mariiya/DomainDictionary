import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  template: `
    <div class="card" style="max-width: 400px; margin: 40px auto;">
      <h2>Реєстрація</h2>
      @if (error) {
        <p class="error-message">{{ error }}</p>
      }
      @if (success) {
        <p style="color: green;">Реєстрація успішна! <a routerLink="/login">Увійти</a></p>
      }
      <form (ngSubmit)="onSubmit()">
        <div class="form-group">
          <label>Ім'я</label>
          <input type="text" [(ngModel)]="name" name="name" required minlength="3">
        </div>
        <div class="form-group">
          <label>Email</label>
          <input type="email" [(ngModel)]="email" name="email" required>
        </div>
        <div class="form-group">
          <label>Пароль</label>
          <input type="password" [(ngModel)]="password" name="password" required minlength="6">
        </div>
        <button class="btn btn-primary" type="submit" [disabled]="loading">
          {{ loading ? 'Зачекайте...' : 'Зареєструватися' }}
        </button>
      </form>
      <p style="margin-top: 16px;">Вже є акаунт? <a routerLink="/login">Увійти</a></p>
    </div>
  `
})
export class RegisterComponent {
  name = '';
  email = '';
  password = '';
  error = '';
  success = false;
  loading = false;

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit(): void {
    this.loading = true;
    this.error = '';
    this.auth.signup({ name: this.name, email: this.email, password: this.password }).subscribe({
      next: () => {
        this.success = true;
        this.loading = false;
      },
      error: (err) => {
        this.error = err.error?.error || 'Помилка реєстрації';
        this.loading = false;
      }
    });
  }
}
