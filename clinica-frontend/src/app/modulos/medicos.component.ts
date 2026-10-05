import { Component, inject } from '@angular/core';
import { MODULOS } from '../core/modulos.config';
import { GestionTablaComponent } from '../shared/gestion-tabla/gestion-tabla.component';
import { MedicoService } from './medicos.service';

@Component({
  selector: 'app-medicos',
  standalone: true,
  imports: [GestionTablaComponent],
  template: '<app-gestion-tabla [config]="config" [servicio]="servicio" />',
})
export class MedicoComponent {
  readonly servicio = inject(MedicoService);
  readonly config = MODULOS.find((m) => m.ruta === 'medicos')!;
}
