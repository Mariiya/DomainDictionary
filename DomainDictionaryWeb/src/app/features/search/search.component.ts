import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SearchService } from '../../core/search.service';
import { SearchResult } from '../../shared/models/search';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="card">
      <h2>Пошук тлумачень термінів</h2>

      <div style="display: flex; gap: 8px; margin-bottom: 16px;">
        <button class="btn" [class.btn-primary]="mode === 'terms'" (click)="mode = 'terms'">Введення термінів</button>
        <button class="btn" [class.btn-primary]="mode === 'text'" (click)="mode = 'text'">Вставка тексту</button>
        <button class="btn" [class.btn-primary]="mode === 'file'" (click)="mode = 'file'">Завантаження файлу</button>
      </div>

      @if (mode === 'terms') {
        <div class="form-group">
          <label>Терміни (через кому)</label>
          <input type="text" [(ngModel)]="termsInput" placeholder="Слон, Мрія, Порцелянова ваза">
        </div>
      }

      @if (mode === 'text') {
        <div class="form-group">
          <label>Текст для аналізу</label>
          <textarea [(ngModel)]="textInput" rows="6" placeholder="Вставте текст для витягу ключових термінів..."></textarea>
        </div>
        <button class="btn btn-secondary" (click)="extractFromText()" [disabled]="loading">Витягти терміни</button>
      }

      @if (mode === 'file') {
        <div class="form-group">
          <label>Файл (.txt)</label>
          <input type="file" accept=".txt" (change)="onFileSelected($event)">
        </div>
        <button class="btn btn-secondary" (click)="extractFromFile()" [disabled]="loading || !selectedFile">Витягти терміни</button>
      }

      @if (extractedTerms.length > 0) {
        <div style="margin: 12px 0;">
          <strong>Знайдені ключові терміни:</strong>
          <p>{{ extractedTerms.join(', ') }}</p>
        </div>
      }

      <div class="form-group" style="margin-top: 16px;">
        <label>
          <input type="checkbox" [(ngModel)]="analyzeEnabled">
          Увімкнути аналіз предметної області
        </label>
      </div>

      <button class="btn btn-primary" (click)="search()" [disabled]="loading" style="margin-top: 8px;">
        {{ loading ? 'Пошук...' : 'Шукати тлумачення' }}
      </button>

      @if (error) {
        <p class="error-message" style="margin-top: 8px;">{{ error }}</p>
      }
    </div>

    @if (results.length > 0) {
      <div class="card" style="margin-top: 16px;">
        <h3>Результати</h3>
        @for (result of results; track result.term) {
          <div style="margin-bottom: 16px; padding-bottom: 16px; border-bottom: 1px solid #eee;">
            <strong style="font-size: 16px;">{{ result.term }}</strong>
            <span style="color: #999; font-size: 12px; margin-left: 8px;">[{{ result.source }}]</span>
            @if (result.definitions.length > 0) {
              <ol style="margin-top: 4px; padding-left: 20px;">
                @for (def of result.definitions; track $index) {
                  <li>{{ def }}</li>
                }
              </ol>
            } @else {
              <p style="color: #999; margin-top: 4px;">Тлумачення не знайдено</p>
            }
          </div>
        }

        <div style="margin-top: 16px; display: flex; gap: 8px;">
          <select [(ngModel)]="saveType">
            <option value="GLOSSARY">Глосарій</option>
            <option value="DOMAIN">Словник ПрО</option>
          </select>
          <input type="text" [(ngModel)]="saveName" placeholder="Назва документу">
          <button class="btn btn-primary" (click)="saveAsPdf()">Зберегти PDF</button>
        </div>
      </div>
    }
  `
})
export class SearchComponent {
  mode: 'terms' | 'text' | 'file' = 'terms';
  termsInput = '';
  textInput = '';
  selectedFile: File | null = null;
  extractedTerms: string[] = [];
  analyzeEnabled = false;
  results: SearchResult[] = [];
  loading = false;
  error = '';
  saveType = 'GLOSSARY';
  saveName = '';

  constructor(private searchService: SearchService, private router: Router) {}

  extractFromText(): void {
    this.loading = true;
    this.searchService.extractKeywordsFromText(this.textInput).subscribe({
      next: (terms) => {
        this.extractedTerms = terms;
        this.termsInput = terms.join(', ');
        this.loading = false;
      },
      error: () => { this.error = 'Помилка витягу термінів'; this.loading = false; }
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFile = input.files?.[0] || null;
  }

  extractFromFile(): void {
    if (!this.selectedFile) return;
    this.loading = true;
    this.searchService.extractKeywordsFromFile(this.selectedFile).subscribe({
      next: (terms) => {
        this.extractedTerms = terms;
        this.termsInput = terms.join(', ');
        this.loading = false;
      },
      error: () => { this.error = 'Помилка витягу термінів з файлу'; this.loading = false; }
    });
  }

  search(): void {
    const terms = this.extractedTerms.length > 0
      ? this.extractedTerms
      : this.termsInput.split(',').map(t => t.trim()).filter(t => t);

    if (terms.length === 0) {
      this.error = 'Введіть хоча б один термін';
      return;
    }

    this.loading = true;
    this.error = '';
    this.searchService.search(terms, this.analyzeEnabled).subscribe({
      next: (results) => {
        this.results = results;
        this.loading = false;
      },
      error: () => { this.error = 'Помилка пошуку'; this.loading = false; }
    });
  }

  saveAsPdf(): void {
    if (!this.saveName) {
      this.error = 'Введіть назву документу';
      return;
    }
    import('../../core/resource.service').then(m => {
      // Using dynamic import to get ResourceService
    });
    // For simplicity, trigger download via form
    const entries = this.results.map(r => ({ term: r.term, definitions: r.definitions }));
    const request = { name: this.saveName, language: 'uk', type: this.saveType as 'GLOSSARY' | 'DOMAIN', entries };

    fetch(`${location.origin.replace(':4200', ':8888')}/api/resources/save`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('dd_token')}`
      },
      body: JSON.stringify(request)
    }).then(res => res.blob()).then(blob => {
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `${this.saveName}.pdf`;
      a.click();
      URL.revokeObjectURL(url);
    });
  }
}
