import { Component, inject } from '@angular/core';
import { MODULOS } from '../core/modulos.config';
import { GestionTablaComponent } from '../shared/gestion-tabla/gestion-tabla.component';
import { MascotaService } from './mascotas.service';

@Component({
  selector: 'app-mascotas',
  standalone: true,
  imports: [GestionTablaComponent],
  template: '<app-gestion-tabla [config]="config" [servicio]="servicio" />',
})
export class MascotaComponent {
  readonly servicio = inject(MascotaService);
  readonly config = MODULOS.find((m) => m.ruta === 'mascotas')!;
}
