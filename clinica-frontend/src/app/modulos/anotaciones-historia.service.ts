import { Injectable } from '@angular/core';
import { CrudService, Filtro } from '../core/crud.service';

@Injectable({ providedIn: 'root' })
export class AnotacionHistoriaService extends CrudService {
  constructor() {
    super('historias-medicas');
  }

  protected override urlListar(filtro: Filtro): string {
    return `${this.base}/${filtro.padreId}/anotaciones`;
  }
  protected override urlCrear(): string {
    return `${this.base}/anotaciones`;
  }
  protected override urlActualizar(id: number | string): string {
    return `${this.base}/anotaciones/${id}`;
  }
}
