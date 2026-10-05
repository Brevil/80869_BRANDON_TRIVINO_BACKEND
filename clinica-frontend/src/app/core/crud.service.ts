import { HttpClient, HttpParams } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from './api.config';

/** Filtros opcionales que algunos listados del backend necesitan. */
export interface Filtro {
  fechaInicial?: string;
  fechaFinal?: string;
  padreId?: number;
}

/** Servicio base: lista, crea y actualiza un recurso REST del backend. */
export abstract class CrudService<T = any> {
  protected readonly http = inject(HttpClient);

  protected constructor(protected readonly recurso: string) {}

  protected get base(): string {
    return `${API_URL}/${this.recurso}`;
  }

  protected urlListar(_filtro: Filtro): string {
    return this.base;
  }
  protected urlCrear(): string {
    return this.base;
  }
  protected urlActualizar(id: number | string): string {
    return `${this.base}/${id}`;
  }

  listar(filtro: Filtro = {}): Observable<T[]> {
    let params = new HttpParams();
    if (filtro.fechaInicial) params = params.set('fechaInicial', filtro.fechaInicial);
    if (filtro.fechaFinal) params = params.set('fechaFinal', filtro.fechaFinal);
    return this.http.get<T[]>(this.urlListar(filtro), { params });
  }

  crear(cuerpo: Partial<T>): Observable<T> {
    return this.http.post<T>(this.urlCrear(), cuerpo);
  }

  actualizar(id: number | string, cuerpo: Partial<T>): Observable<T> {
    return this.http.put<T>(this.urlActualizar(id), cuerpo);
  }
}
