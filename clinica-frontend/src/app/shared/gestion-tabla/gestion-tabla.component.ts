import { Component, OnInit, input, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Campo, Columna, ModuloConfig } from '../../core/modulos.config';
import { CrudService, Filtro } from '../../core/crud.service';

type Fila = Record<string, any>;

/** Tabla de gestión reutilizable: listado + botón "Nuevo" + botón "Editar" por fila. */
@Component({
  selector: 'app-gestion-tabla',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './gestion-tabla.component.html',
  styleUrl: './gestion-tabla.component.css',
})
export class GestionTablaComponent implements OnInit {
  readonly config = input.required<ModuloConfig>();
  readonly servicio = input.required<CrudService>();

  readonly filas = signal<Fila[]>([]);
  readonly cargando = signal(false);
  readonly error = signal('');
  readonly exito = signal('');

  // Filtros
  fechaInicial = '2020-01-01T00:00';
  fechaFinal = this.enUnAnio();
  padreId: number | null = null;

  // Modal
  readonly modalAbierto = signal(false);
  readonly editandoId = signal<number | string | null>(null);
  readonly guardando = signal(false);
  modelo: Fila = {};

  ngOnInit(): void {
    if (this.config().listado !== 'porPadre') this.cargar();
  }

  cargar(): void {
    const cfg = this.config();
    const filtro: Filtro = {};
    if (cfg.listado === 'rangoFechas') {
      filtro.fechaInicial = this.aIso(this.fechaInicial);
      filtro.fechaFinal = this.aIso(this.fechaFinal);
    }
    if (cfg.listado === 'porPadre') {
      if (this.padreId == null) {
        this.error.set(`Ingresa el ${cfg.etiquetaPadre?.toLowerCase()} para consultar.`);
        return;
      }
      filtro.padreId = this.padreId;
    }
    this.cargando.set(true);
    this.error.set('');
    this.servicio().listar(filtro).subscribe({
      next: (datos) => {
        this.filas.set(datos ?? []);
        this.cargando.set(false);
      },
      error: (e) => {
        this.filas.set([]);
        this.cargando.set(false);
        this.error.set(this.mensajeError(e, 'No se pudo cargar el listado.'));
      },
    });
  }

  nuevo(): void {
    this.editandoId.set(null);
    this.modelo = {};
    for (const c of this.config().campos) {
      if (c.tipo === 'checkbox') this.modelo[c.clave] = true;
    }
    if (this.config().listado === 'porPadre' && this.padreId != null) {
      this.modelo['historiaId'] = this.padreId;
    }
    this.exito.set('');
    this.modalAbierto.set(true);
  }

  editar(fila: Fila): void {
    this.editandoId.set(fila[this.config().idCampo]);
    this.modelo = {};
    for (const c of this.config().campos) {
      let v = this.valor(fila, c.clave);
      if (v == null && c.clave === 'especializacionId') v = fila['especializacion']?.id;
      if (c.tipo === 'datetime' && typeof v === 'string') v = v.slice(0, 16);
      if (c.tipo === 'date' && typeof v === 'string') v = v.slice(0, 10);
      this.modelo[c.clave] = v;
    }
    this.exito.set('');
    this.modalAbierto.set(true);
  }

  cerrar(): void {
    this.modalAbierto.set(false);
  }

  guardar(invalido: boolean | null): void {
    if (invalido) return;
    const cuerpo: Fila = {};
    for (const c of this.config().campos) {
      let v = this.modelo[c.clave];
      if (v === '' || v === undefined) v = null;
      if (c.tipo === 'datetime' && v) v = this.aIso(v);
      cuerpo[c.clave] = v;
    }
    const id = this.editandoId();
    const peticion = id == null ? this.servicio().crear(cuerpo) : this.servicio().actualizar(id, cuerpo);
    this.guardando.set(true);
    peticion.subscribe({
      next: () => {
        this.guardando.set(false);
        this.modalAbierto.set(false);
        this.exito.set(id == null ? 'Registro creado correctamente.' : 'Registro actualizado correctamente.');
        this.cargar();
      },
      error: (e) => {
        this.guardando.set(false);
        this.error.set(this.mensajeError(e, 'No se pudo guardar el registro.'));
        this.modalAbierto.set(false);
      },
    });
  }

  // ---- Utilidades de presentación ----
  valor(fila: Fila, clave: string): any {
    return clave.split('.').reduce((o, k) => (o == null ? o : o[k]), fila);
  }

  formatear(fila: Fila, col: Columna): string {
    const v = this.valor(fila, col.clave);
    if (v == null || v === '') return '—';
    switch (col.tipo) {
      case 'bool':
        return v ? 'Sí' : 'No';
      case 'fecha': {
        const [a, m, d] = String(v).slice(0, 10).split('-');
        return `${d}/${m}/${a}`;
      }
      case 'fechahora': {
        const s = String(v);
        const [a, m, d] = s.slice(0, 10).split('-');
        return `${d}/${m}/${a} ${s.slice(11, 16)}`;
      }
      default:
        return String(v);
    }
  }

  trackFila = (_: number, f: Fila) => f[this.config().idCampo];
  campoId = (c: Campo) => `campo-${c.clave}`;

  private aIso(v: string): string {
    return v.length === 16 ? `${v}:00` : v;
  }

  private enUnAnio(): string {
    const d = new Date();
    d.setFullYear(d.getFullYear() + 1);
    const p = (n: number) => String(n).padStart(2, '0');
    return `${d.getFullYear()}-${p(d.getMonth() + 1)}-${p(d.getDate())}T23:59`;
  }

  private mensajeError(e: any, porDefecto: string): string {
    if (e?.status === 0) return 'No hay conexión con el backend (¿está corriendo en http://localhost:8080?).';
    if (e?.status === 404 || e?.status === 405) return 'El backend no expone esta operación todavía.';
    return e?.error?.message || porDefecto;
  }
}
