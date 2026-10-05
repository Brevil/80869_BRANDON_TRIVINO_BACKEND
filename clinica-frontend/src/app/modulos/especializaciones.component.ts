import { Component, inject } from '@angular/core';
import { MODULOS } from '../core/modulos.config';
import { GestionTablaComponent } from '../shared/gestion-tabla/gestion-tabla.component';
import { EspecializacionService } from './especializaciones.service';

@Component({
  selector: 'app-especializaciones',
  standalone: true,
  imports: [GestionTablaComponent],
  template: '<app-gestion-tabla [config]="config" [servicio]="servicio" />',
})
export class EspecializacionComponent {
  readonly servicio = inject(EspecializacionService);
  readonly config = MODULOS.find((m) => m.ruta === 'especializaciones')!;
}
