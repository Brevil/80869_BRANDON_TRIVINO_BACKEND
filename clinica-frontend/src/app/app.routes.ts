import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'formulas-medicas' },
  {
    path: 'formulas-medicas',
    loadComponent: () => import('./modulos/formula-medica.component').then((m) => m.FormulaMedicaComponent),
  },
  { path: 'citas', loadComponent: () => import('./modulos/citas.component').then((m) => m.CitaComponent) },
  { path: 'clientes', loadComponent: () => import('./modulos/clientes.component').then((m) => m.ClienteComponent) },
  { path: 'mascotas', loadComponent: () => import('./modulos/mascotas.component').then((m) => m.MascotaComponent) },
  { path: 'razas', loadComponent: () => import('./modulos/razas.component').then((m) => m.RazaComponent) },
  { path: 'medicos', loadComponent: () => import('./modulos/medicos.component').then((m) => m.MedicoComponent) },
  {
    path: 'especializaciones',
    loadComponent: () => import('./modulos/especializaciones.component').then((m) => m.EspecializacionComponent),
  },
  {
    path: 'medicamentos',
    loadComponent: () => import('./modulos/medicamentos.component').then((m) => m.MedicamentoComponent),
  },
  {
    path: 'historias-medicas',
    loadComponent: () => import('./modulos/historias-medicas.component').then((m) => m.HistoriaMedicaComponent),
  },
  {
    path: 'anotaciones-historia',
    loadComponent: () =>
      import('./modulos/anotaciones-historia.component').then((m) => m.AnotacionHistoriaComponent),
  },
  { path: '**', redirectTo: 'formulas-medicas' },
];
