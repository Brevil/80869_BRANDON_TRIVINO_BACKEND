import { Injectable } from '@angular/core';
import { CrudService, Filtro } from '../core/crud.service';


@Injectable({ providedIn: 'root' })
export class CitaService extends CrudService {
  constructor() {
    super('citas');
  }

}
