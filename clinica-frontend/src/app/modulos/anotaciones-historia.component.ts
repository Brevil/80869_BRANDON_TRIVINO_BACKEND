import { Component, inject } from '@angular/core';
import { MODULOS } from '../core/modulos.config';
import { GestionTablaComponent } from '../shared/gestion-tabla/gestion-tabla.component';
import { AnotacionHistoriaService } from './anotaciones-historia.service';

@Component({
  selector: 'app-anotaciones-historia',
  standalone: true,
  imports: [GestionTablaComponent],
  template: '<app-gestion-tabla [config]="config" [servicio]="servicio" />',
})
export class AnotacionHistoriaComponent {
  readonly servicio = inject(AnotacionHistoriaService);
  readonly config = MODULOS.find((m) => m.ruta === 'anotaciones-historia')!;
}
