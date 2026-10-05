import { Component, inject } from '@angular/core';
import { MODULOS } from '../core/modulos.config';
import { GestionTablaComponent } from '../shared/gestion-tabla/gestion-tabla.component';
import { CitaService } from './citas.service';

@Component({
  selector: 'app-citas',
  standalone: true,
  imports: [GestionTablaComponent],
  template: '<app-gestion-tabla [config]="config" [servicio]="servicio" />',
})
export class CitaComponent {
  readonly servicio = inject(CitaService);
  readonly config = MODULOS.find((m) => m.ruta === 'citas')!;
}
