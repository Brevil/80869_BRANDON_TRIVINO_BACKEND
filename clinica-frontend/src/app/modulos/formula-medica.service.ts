import { Injectable } from '@angular/core';
import { CrudService, Filtro } from '../core/crud.service';


@Injectable({ providedIn: 'root' })
export class FormulaMedicaService extends CrudService {
  constructor() {
    super('formulas-medicas');
  }

}
