import { Component, inject } from '@angular/core';
import { MODULOS } from '../core/modulos.config';
import { GestionTablaComponent } from '../shared/gestion-tabla/gestion-tabla.component';
import { RazaService } from './razas.service';

@Component({
  selector: 'app-razas',
  standalone: true,
  imports: [GestionTablaComponent],
  template: '<app-gestion-tabla [config]="config" [servicio]="servicio" />',
})
export class RazaComponent {
  readonly servicio = inject(RazaService);
  readonly config = MODULOS.find((m) => m.ruta === 'razas')!;
}
