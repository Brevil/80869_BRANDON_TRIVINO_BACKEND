/** Descripción declarativa de un módulo de gestión (una tabla de la BD). */
export interface Columna {
  clave: string; // admite rutas con punto: "especializacion.nombre"
  etiqueta: string;
  tipo?: 'texto' | 'fecha' | 'fechahora' | 'bool';
}

export interface Campo {
  clave: string;
  etiqueta: string;
  tipo: 'text' | 'number' | 'textarea' | 'date' | 'datetime' | 'select' | 'checkbox';
  requerido?: boolean;
  opciones?: string[];
}

export interface ModuloConfig {
  ruta: string;
  titulo: string; // texto del menú y del encabezado
  tabla: string; // tabla de la BD que representa
  idCampo: string;
  columnas: Columna[];
  campos: Campo[];
  listado: 'todos' | 'rangoFechas' | 'porPadre';
  etiquetaPadre?: string;
  /** Aviso cuando el backend todavía no expone todas las operaciones. */
  aviso?: string;
}

const AVISO_SIN_API =
  'El backend aún no expone este recurso REST; el módulo funcionará cuando exista el endpoint.';

export const MODULOS: ModuloConfig[] = [
  {
    ruta: 'formulas-medicas',
    titulo: 'Gestión de Fórmulas Médicas',
    tabla: 'formula_medica',
    idCampo: 'id',
    listado: 'todos',
    aviso: 'El backend solo expone el listado (GET); Nuevo y Editar requieren POST/PUT en el backend.',
    columnas: [
      { clave: 'id', etiqueta: 'ID' },
      { clave: 'citaId', etiqueta: 'Cita' },
      { clave: 'medicamentoId', etiqueta: 'Medicamento' },
      { clave: 'dosis', etiqueta: 'Dosis' },
      { clave: 'indicaciones', etiqueta: 'Indicaciones' },
      { clave: 'fechaCreacionRegistro', etiqueta: 'Fecha creación', tipo: 'fechahora' },
      { clave: 'fechaActualizacionRegistro', etiqueta: 'Fecha actualización', tipo: 'fechahora' },
    ],
    campos: [
      { clave: 'citaId', etiqueta: 'ID de la cita', tipo: 'number', requerido: true },
      { clave: 'medicamentoId', etiqueta: 'ID del medicamento', tipo: 'number', requerido: true },
      { clave: 'dosis', etiqueta: 'Dosis', tipo: 'textarea', requerido: true },
      { clave: 'indicaciones', etiqueta: 'Indicaciones', tipo: 'textarea' },
    ],
  },
  {
    ruta: 'citas',
    titulo: 'Gestión de Citas',
    tabla: 'cita',
    idCampo: 'id',
    listado: 'rangoFechas',
    columnas: [
      { clave: 'id', etiqueta: 'ID' },
      { clave: 'clienteId', etiqueta: 'Cliente' },
      { clave: 'mascotaId', etiqueta: 'Mascota' },
      { clave: 'medicoId', etiqueta: 'Médico' },
      { clave: 'fechaHora', etiqueta: 'Fecha y hora', tipo: 'fechahora' },
      { clave: 'estado', etiqueta: 'Estado' },
      { clave: 'motivo', etiqueta: 'Motivo' },
    ],
    campos: [
      { clave: 'clienteId', etiqueta: 'ID del cliente', tipo: 'number', requerido: true },
      { clave: 'mascotaId', etiqueta: 'ID de la mascota', tipo: 'number', requerido: true },
      { clave: 'medicoId', etiqueta: 'ID del médico', tipo: 'number', requerido: true },
      { clave: 'fechaHora', etiqueta: 'Fecha y hora', tipo: 'datetime', requerido: true },
      { clave: 'estado', etiqueta: 'Estado', tipo: 'select', requerido: true,
        opciones: ['programada', 'atendida', 'cancelada'] },
      { clave: 'motivo', etiqueta: 'Motivo', tipo: 'textarea' },
    ],
  },
  {
    ruta: 'clientes',
    titulo: 'Gestión de Clientes',
    tabla: 'cliente',
    idCampo: 'clienteId',
    listado: 'todos',
    aviso: AVISO_SIN_API,
    columnas: [
      { clave: 'clienteId', etiqueta: 'ID' },
      { clave: 'tipoDocumento', etiqueta: 'Tipo doc.' },
      { clave: 'numeroDocumento', etiqueta: 'Documento' },
      { clave: 'nombres', etiqueta: 'Nombres' },
      { clave: 'apellidos', etiqueta: 'Apellidos' },
      { clave: 'fechaNacimiento', etiqueta: 'Nacimiento', tipo: 'fecha' },
      { clave: 'genero', etiqueta: 'Género' },
      { clave: 'telefono', etiqueta: 'Teléfono' },
      { clave: 'direccion', etiqueta: 'Dirección' },
      { clave: 'activo', etiqueta: 'Activo', tipo: 'bool' },
    ],
    campos: [
      { clave: 'tipoDocumento', etiqueta: 'Tipo de documento', tipo: 'select', requerido: true,
        opciones: ['CC', 'TI', 'CE'] },
      { clave: 'numeroDocumento', etiqueta: 'Número de documento', tipo: 'text', requerido: true },
      { clave: 'nombres', etiqueta: 'Nombres', tipo: 'text', requerido: true },
      { clave: 'apellidos', etiqueta: 'Apellidos', tipo: 'text', requerido: true },
      { clave: 'fechaNacimiento', etiqueta: 'Fecha de nacimiento', tipo: 'date', requerido: true },
      { clave: 'genero', etiqueta: 'Género', tipo: 'select', opciones: ['M', 'F'] },
      { clave: 'telefono', etiqueta: 'Teléfono', tipo: 'text' },
      { clave: 'direccion', etiqueta: 'Dirección', tipo: 'textarea' },
      { clave: 'activo', etiqueta: 'Activo', tipo: 'checkbox' },
    ],
  },
  {
    ruta: 'mascotas',
    titulo: 'Gestión de Mascotas',
    tabla: 'mascota',
    idCampo: 'mascotaId',
    listado: 'todos',
    aviso: AVISO_SIN_API,
    columnas: [
      { clave: 'mascotaId', etiqueta: 'ID' },
      { clave: 'nombreMascota', etiqueta: 'Nombre' },
      { clave: 'edad', etiqueta: 'Edad' },
      { clave: 'razaId', etiqueta: 'Raza' },
      { clave: 'clienteId', etiqueta: 'Cliente' },
      { clave: 'fechaRegistro', etiqueta: 'Registro', tipo: 'fechahora' },
      { clave: 'fechaModificacion', etiqueta: 'Modificación', tipo: 'fechahora' },
    ],
    campos: [
      { clave: 'nombreMascota', etiqueta: 'Nombre', tipo: 'text', requerido: true },
      { clave: 'edad', etiqueta: 'Edad', tipo: 'number', requerido: true },
      { clave: 'razaId', etiqueta: 'ID de la raza', tipo: 'number', requerido: true },
      { clave: 'clienteId', etiqueta: 'ID del cliente', tipo: 'number', requerido: true },
    ],
  },
  {
    ruta: 'razas',
    titulo: 'Gestión de Razas',
    tabla: 'raza',
    idCampo: 'razaId',
    listado: 'todos',
    aviso: AVISO_SIN_API,
    columnas: [
      { clave: 'razaId', etiqueta: 'ID' },
      { clave: 'nombre', etiqueta: 'Nombre' },
      { clave: 'especie', etiqueta: 'Especie' },
      { clave: 'fechaCreacion', etiqueta: 'Creación', tipo: 'fechahora' },
      { clave: 'fechaModificacion', etiqueta: 'Modificación', tipo: 'fechahora' },
    ],
    campos: [
      { clave: 'nombre', etiqueta: 'Nombre', tipo: 'text', requerido: true },
      { clave: 'especie', etiqueta: 'Especie', tipo: 'text', requerido: true },
    ],
  },
  {
    ruta: 'medicos',
    titulo: 'Gestión de Médicos',
    tabla: 'medico',
    idCampo: 'id',
    listado: 'todos',
    aviso: 'El backend solo expone el listado (GET); Nuevo y Editar requieren POST/PUT en el backend.',
    columnas: [
      { clave: 'id', etiqueta: 'ID' },
      { clave: 'tipoDocumento', etiqueta: 'Tipo doc.' },
      { clave: 'numeroDocumento', etiqueta: 'Documento' },
      { clave: 'nombres', etiqueta: 'Nombres' },
      { clave: 'apellidos', etiqueta: 'Apellidos' },
      { clave: 'telefono', etiqueta: 'Teléfono' },
      { clave: 'registroProfesional', etiqueta: 'Registro prof.' },
      { clave: 'especializacion.nombre', etiqueta: 'Especialización' },
    ],
    campos: [
      { clave: 'tipoDocumento', etiqueta: 'Tipo de documento', tipo: 'select', requerido: true,
        opciones: ['CC', 'TI', 'CE'] },
      { clave: 'numeroDocumento', etiqueta: 'Número de documento', tipo: 'text', requerido: true },
      { clave: 'nombres', etiqueta: 'Nombres', tipo: 'text', requerido: true },
      { clave: 'apellidos', etiqueta: 'Apellidos', tipo: 'text', requerido: true },
      { clave: 'telefono', etiqueta: 'Teléfono', tipo: 'text' },
      { clave: 'registroProfesional', etiqueta: 'Registro profesional', tipo: 'text', requerido: true },
      { clave: 'especializacionId', etiqueta: 'ID de especialización', tipo: 'number', requerido: true },
    ],
  },
  {
    ruta: 'especializaciones',
    titulo: 'Gestión de Especializaciones',
    tabla: 'especializacion',
    idCampo: 'id',
    listado: 'todos',
    aviso: AVISO_SIN_API,
    columnas: [
      { clave: 'id', etiqueta: 'ID' },
      { clave: 'nombre', etiqueta: 'Nombre' },
      { clave: 'descripcion', etiqueta: 'Descripción' },
      { clave: 'codigoEspecializacion', etiqueta: 'Código' },
    ],
    campos: [
      { clave: 'nombre', etiqueta: 'Nombre', tipo: 'text', requerido: true },
      { clave: 'descripcion', etiqueta: 'Descripción', tipo: 'textarea' },
      { clave: 'codigoEspecializacion', etiqueta: 'Código', tipo: 'text', requerido: true },
    ],
  },
  {
    ruta: 'medicamentos',
    titulo: 'Gestión de Medicamentos',
    tabla: 'medicamento',
    idCampo: 'id',
    listado: 'todos',
    aviso: AVISO_SIN_API,
    columnas: [
      { clave: 'id', etiqueta: 'ID' },
      { clave: 'nombre', etiqueta: 'Nombre' },
      { clave: 'descripcion', etiqueta: 'Descripción' },
      { clave: 'presentacion', etiqueta: 'Presentación' },
      { clave: 'fechaCompra', etiqueta: 'Compra', tipo: 'fecha' },
      { clave: 'fechaVence', etiqueta: 'Vence', tipo: 'fecha' },
    ],
    campos: [
      { clave: 'nombre', etiqueta: 'Nombre', tipo: 'text', requerido: true },
      { clave: 'descripcion', etiqueta: 'Descripción', tipo: 'textarea' },
      { clave: 'presentacion', etiqueta: 'Presentación', tipo: 'text' },
      { clave: 'fechaCompra', etiqueta: 'Fecha de compra', tipo: 'date', requerido: true },
      { clave: 'fechaVence', etiqueta: 'Fecha de vencimiento', tipo: 'date', requerido: true },
    ],
  },
  {
    ruta: 'historias-medicas',
    titulo: 'Gestión de Historias Médicas',
    tabla: 'historia_medica',
    idCampo: 'id',
    listado: 'rangoFechas',
    columnas: [
      { clave: 'id', etiqueta: 'ID' },
      { clave: 'pacienteId', etiqueta: 'Paciente' },
      { clave: 'fechaCreacion', etiqueta: 'Fecha creación', tipo: 'fechahora' },
    ],
    campos: [{ clave: 'pacienteId', etiqueta: 'ID del paciente', tipo: 'number', requerido: true }],
  },
  {
    ruta: 'anotaciones-historia',
    titulo: 'Gestión de Anotaciones de Historia',
    tabla: 'anotacion_historia',
    idCampo: 'id',
    listado: 'porPadre',
    etiquetaPadre: 'ID de la historia médica',
    columnas: [
      { clave: 'id', etiqueta: 'ID' },
      { clave: 'historiaId', etiqueta: 'Historia' },
      { clave: 'medicoId', etiqueta: 'Médico' },
      { clave: 'fecha', etiqueta: 'Fecha', tipo: 'fechahora' },
      { clave: 'descripcion', etiqueta: 'Descripción' },
    ],
    campos: [
      { clave: 'historiaId', etiqueta: 'ID de la historia', tipo: 'number', requerido: true },
      { clave: 'medicoId', etiqueta: 'ID del médico', tipo: 'number', requerido: true },
      { clave: 'descripcion', etiqueta: 'Descripción', tipo: 'textarea', requerido: true },
    ],
  },
];
