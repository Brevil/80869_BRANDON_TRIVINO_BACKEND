import { Component, inject } from '@angular/core';
import { MODULOS } from '../core/modulos.config';
import { GestionTablaComponent } from '../shared/gestion-tabla/gestion-tabla.component';
import { FormulaMedicaService } from './formula-medica.service';

@Component({
  selector: 'app-formula-medica',
  standalone: true,
  imports: [GestionTablaComponent],
  template: '<app-gestion-tabla [config]="config" [servicio]="servicio" />',
})
export class FormulaMedicaComponent {
  readonly servicio = inject(FormulaMedicaService);
  readonly config = MODULOS.find((m) => m.ruta === 'formulas-medicas')!;
}
