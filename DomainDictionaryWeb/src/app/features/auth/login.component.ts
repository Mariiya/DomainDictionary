import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  template: `
    <div class="card" style="max-width: 400px; margin: 40px auto;">
      <h2>Вхід</h2>
      @if (error) {
        <p class="error-message">{{ error }}</p>
      }
      <form (ngSubmit)="onSubmit()">
        <div class="form-group">
          <label>Email</label>
          <input type="email" [(ngModel)]="email" name="email" required>
        </div>
        <div class="form-group">
          <label>Пароль</label>
          <input type="password" [(ngModel)]="password" name="password" required>
        </div>
        <button class="btn btn-primary" type="submit" [disabled]="loading">
          {{ loading ? 'Зачекайте...' : 'Увійти' }}
        </button>
      </form>
      <p style="margin-top: 16px;">Немає акаунту? <a routerLink="/register">Зареєструватися</a></p>
    </div>
  `
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';
  loading = false;

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit(): void {
    this.loading = true;
    this.error = '';
    this.auth.login({ email: this.email, password: this.password }).subscribe({
      next: () => {
        this.router.navigate(['/search']);
      },
      error: () => {
        this.error = 'Невірний email або пароль';
        this.loading = false;
      }
    });
  }
}
