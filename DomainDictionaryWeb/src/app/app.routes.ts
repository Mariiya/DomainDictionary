import { Routes } from '@angular/router';
import { authGuard } from './core/auth.guard';

export const routes: Routes = [
  { path: 'login', loadComponent: () => import('./features/auth/login.component').then(m => m.LoginComponent) },
  { path: 'register', loadComponent: () => import('./features/auth/register.component').then(m => m.RegisterComponent) },
  { path: 'search', loadComponent: () => import('./features/search/search.component').then(m => m.SearchComponent), canActivate: [authGuard] },
  { path: 'results', loadComponent: () => import('./features/results/results.component').then(m => m.ResultsComponent), canActivate: [authGuard] },
  { path: 'resources', loadComponent: () => import('./features/resources/resources.component').then(m => m.ResourcesComponent), canActivate: [authGuard] },
  { path: 'upload', loadComponent: () => import('./features/dictionary-wizard/dictionary-wizard.component').then(m => m.DictionaryWizardComponent), canActivate: [authGuard] },
  { path: '', redirectTo: '/search', pathMatch: 'full' },
  { path: '**', redirectTo: '/search' }
];
