import { Injectable } from '@angular/core';
import { CrudService, Filtro } from '../core/crud.service';


@Injectable({ providedIn: 'root' })
export class MedicoService extends CrudService {
  constructor() {
    super('medicos');
  }

}
