import { Injectable } from '@angular/core';
import { CrudService, Filtro } from '../core/crud.service';


@Injectable({ providedIn: 'root' })
export class ClienteService extends CrudService {
  constructor() {
    super('clientes');
  }

}
