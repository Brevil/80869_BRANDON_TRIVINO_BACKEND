import { Component, inject } from '@angular/core';
import { MODULOS } from '../core/modulos.config';
import { GestionTablaComponent } from '../shared/gestion-tabla/gestion-tabla.component';
import { ClienteService } from './clientes.service';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [GestionTablaComponent],
  template: '<app-gestion-tabla [config]="config" [servicio]="servicio" />',
})
export class ClienteComponent {
  readonly servicio = inject(ClienteService);
  readonly config = MODULOS.find((m) => m.ruta === 'clientes')!;
}
