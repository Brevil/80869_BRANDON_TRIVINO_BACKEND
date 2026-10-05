import { Component, inject } from '@angular/core';
import { MODULOS } from '../core/modulos.config';
import { GestionTablaComponent } from '../shared/gestion-tabla/gestion-tabla.component';
import { HistoriaMedicaService } from './historias-medicas.service';

@Component({
  selector: 'app-historias-medicas',
  standalone: true,
  imports: [GestionTablaComponent],
  template: '<app-gestion-tabla [config]="config" [servicio]="servicio" />',
})
export class HistoriaMedicaComponent {
  readonly servicio = inject(HistoriaMedicaService);
  readonly config = MODULOS.find((m) => m.ruta === 'historias-medicas')!;
}
