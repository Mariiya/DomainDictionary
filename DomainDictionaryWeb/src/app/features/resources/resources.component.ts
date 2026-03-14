import { Component, OnInit } from '@angular/core';
import { ResourceService } from '../../core/resource.service';
import { ElectronicDictionary } from '../../shared/models/dictionary';

@Component({
  selector: 'app-resources',
  standalone: true,
  template: `
    <div class="card">
      <h2>Мої словники</h2>
      @if (dictionaries.length === 0) {
        <p>У вас ще немає збережених словників.</p>
      }
      @for (dict of dictionaries; track dict.id) {
        <div style="display: flex; justify-content: space-between; align-items: center; padding: 12px 0; border-bottom: 1px solid #eee;">
          <div>
            <strong>{{ dict.name }}</strong>
            <span style="color: #999; margin-left: 8px;">{{ dict.type }} | {{ dict.language }}</span>
          </div>
          <button class="btn btn-secondary" (click)="deleteDictionary(dict.id)">Видалити</button>
        </div>
      }
    </div>
  `
})
export class ResourcesComponent implements OnInit {
  dictionaries: ElectronicDictionary[] = [];

  constructor(private resourceService: ResourceService) {}

  ngOnInit(): void {
    this.resourceService.getMyDictionaries().subscribe({
      next: (data) => this.dictionaries = data,
      error: () => {}
    });
  }

  deleteDictionary(id: number): void {
    this.resourceService.deleteDictionary(id).subscribe({
      next: () => this.dictionaries = this.dictionaries.filter(d => d.id !== id)
    });
  }
}
