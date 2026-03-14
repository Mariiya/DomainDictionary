import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ResourceService } from '../../core/resource.service';

@Component({
  selector: 'app-dictionary-wizard',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="card" style="max-width: 600px; margin: 0 auto;">
      <h2>Завантаження словника</h2>

      @if (success) {
        <p style="color: green;">Словник завантажено та проіндексовано!</p>
      }
      @if (error) {
        <p class="error-message">{{ error }}</p>
      }

      <form (ngSubmit)="upload()">
        <div class="form-group">
          <label>Назва словника</label>
          <input type="text" [(ngModel)]="name" name="name" required>
        </div>
        <div class="form-group">
          <label>Мова</label>
          <select [(ngModel)]="language" name="language">
            <option value="uk">Українська</option>
            <option value="en">English</option>
            <option value="ru">Русский</option>
          </select>
        </div>
        <div class="form-group">
          <label>Файл словника (.txt)</label>
          <input type="file" accept=".txt" (change)="onFileSelected($event)">
        </div>

        <h3 style="margin: 16px 0 8px;">Правила парсингу</h3>
        <div class="form-group">
          <label>Роздільник статей</label>
          <input type="text" [(ngModel)]="articleSeparator" name="articleSep" placeholder="\\n\\n">
        </div>
        <div class="form-group">
          <label>Роздільник терміну і визначення</label>
          <input type="text" [(ngModel)]="termSeparator" name="termSep" placeholder=" - ">
        </div>
        <div class="form-group">
          <label>Роздільник визначень</label>
          <input type="text" [(ngModel)]="definitionSeparator" name="defSep" placeholder=";">
        </div>

        <button class="btn btn-primary" type="submit" [disabled]="loading || !selectedFile">
          {{ loading ? 'Завантаження...' : 'Завантажити' }}
        </button>
      </form>
    </div>
  `
})
export class DictionaryWizardComponent {
  name = '';
  language = 'uk';
  articleSeparator = '\n\n';
  termSeparator = ' - ';
  definitionSeparator = ';';
  selectedFile: File | null = null;
  loading = false;
  error = '';
  success = false;

  constructor(private resourceService: ResourceService) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFile = input.files?.[0] || null;
  }

  upload(): void {
    if (!this.selectedFile || !this.name) return;
    this.loading = true;
    this.error = '';
    this.success = false;

    this.resourceService.uploadDictionary(
      this.selectedFile, this.name, this.language,
      this.articleSeparator, this.termSeparator, this.definitionSeparator
    ).subscribe({
      next: () => { this.success = true; this.loading = false; },
      error: () => { this.error = 'Помилка завантаження'; this.loading = false; }
    });
  }
}
