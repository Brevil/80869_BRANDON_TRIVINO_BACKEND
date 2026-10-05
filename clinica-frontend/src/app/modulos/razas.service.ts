import { Injectable } from '@angular/core';
import { CrudService, Filtro } from '../core/crud.service';


@Injectable({ providedIn: 'root' })
export class RazaService extends CrudService {
  constructor() {
    super('razas');
  }

}
