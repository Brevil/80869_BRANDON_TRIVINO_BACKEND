# Segundo parcial de Programación Web: Clínica veterinaria

Backend Spring Boot (`80869_BRANDON_TRIVINO_BACKEND`) y frontend Angular (`80869_BRANDON_TRIVINO_FRONTEND`), ambos en la rama `80869_BRANDON`.

## 1. Puesta en marcha

1. **Base de datos (MariaDB o MySQL en `localhost:3306`, usuario `root`, contraseña `12345`, según `application-dev.properties`).**
   - Esquema: `mariadb -u root -p < database/clinica.sql`
   - Datos de prueba (UTF-8): `mariadb --default-character-set=utf8mb4 -u root -p clinica < database/datos_prueba.sql`
   - Si la base ya existe con el esquema anterior, las tablas `formula_medica`, `historia_medica` y `anotacion_historia` cambiaron. Hay que recrearlas con el script.
2. **Backend:** `mvn spring-boot:run` (puerto 8080, contexto `/clinica/v1`).
3. **Frontend:** `npm ci` y luego `npm start`. Abrir `http://localhost:4200/#/inicio`.
4. **Pruebas de API:** `bash pruebas/pruebas_api.sh` (con el backend corriendo y la base recién cargada).

## 2. Modelo de datos

| Entidad (tabla) | Relaciones | Campos relevantes |
|---|---|---|
| `Cliente` | 1:N con `Mascota` y `Cita` | documento, nombres, apellidos, activo |
| `Mascota` | N:1 con `Cliente` y `Raza` | nombre, edad |
| `Medico` (veterinario) | 1:N con `Cita`, `Historia`, `Anotación`, `Fórmula` | registro profesional |
| `Cita` | N:1 con `Cliente`, `Mascota` y `Medico` | fecha y hora, motivo, estado |
| `HistoriaMedica` | 1:1 con `Cita` (única por cita); N:1 con `Mascota` y `Medico` | fecha y hora, motivo, diagnóstico, observaciones |
| `AnotacionHistoria` | N:1 con `HistoriaMedica` y `Medico` | fecha, descripción, observación, diagnóstico |
| `FormulaMedica` | N:1 con `Cita`, `Mascota`, `Medico` y `Medicamento` | fecha de emisión, dosis, frecuencia, duración en días, indicaciones |
| `Medicamento` | 1:N con `FormulaMedica` | nombre, presentación |

Relación de la especificación (sección 7): Cliente → Mascota → Cita / Historia / Fórmula. Las claves foráneas no nulas evitan información huérfana.

## 3. Servicios REST (base `/clinica/v1`)

| Método | Ruta | Uso |
|---|---|---|
| GET | `/cliente/listar` | Listar clientes |
| GET | `/cliente/{id}` | Consultar cliente |
| GET | `/mascota/buscar-by-cliente?clienteId=` | Mascotas de un cliente |
| GET | `/mascota/{id}` | Consultar mascota |
| GET | `/medico/listar` | Listar veterinarios |
| GET | `/cita/listar` | Listar citas, de la más reciente a la más antigua |
| GET | `/cita/{id}` | Consultar cita |
| POST | `/cita/crear` | Crear cita |
| PUT | `/cita/actualizar` | Editar cita (según su estado) |
| GET | `/historia-medica/listar` | Listar historias clínicas |
| POST | `/historia-medica/crear` | Crear historia desde una cita atendida |
| POST | `/anotacion-historia/crear` | Agregar anotación a una historia |
| GET | `/anotacion-historia/listar?historiaId=` o `?mascotaId=` | Anotaciones de una historia o del histórico de la mascota |
| GET | `/formula-medica/listar` | Listar fórmulas, por fecha de creación descendente |
| GET | `/formula-medica/{id}` | Consultar fórmula |
| GET | `/formula-medica/medicamentos` | Medicamentos para prescribir |
| POST | `/formula-medica/crear` | Crear fórmula |
| PUT | `/formula-medica/actualizar` | Editar fórmula |

Los errores responden `{status, error, message}`. Códigos: 400 para validaciones y reglas de negocio, 404 cuando el registro no existe, 400 si un id no es numérico, y 500 con mensaje genérico (el detalle queda en el log).

## 4. Reglas de negocio implementadas

**Citas** (`CitaServiceImpl`)
- Cliente, mascota y veterinario deben existir (404). La mascota debe pertenecer al cliente indicado.
- Un veterinario no puede tener dos citas con 15 minutos o menos de diferencia. Se revisa el rango de ±15 minutos, con los extremos incluidos.
- Una mascota no puede tener dos citas en ese mismo rango.
- No se registran citas en fechas anteriores al día actual.
- Estados válidos: PENDIENTE, CONFIRMADA, ATENDIDA, CANCELADA y PROGRAMADA.
- Una cita ATENDIDA no se modifica.
- Una cita CANCELADA no se edita como si estuviera activa. Solo se reactiva cambiando su estado a uno activo, y eso vuelve a validar los horarios.
- Las citas canceladas no cuentan en los cruces de horario.

**Historias clínicas** (`HistoriaMedicaServiceImpl`)
- Solo se crean a partir de una cita ATENDIDA, y una cita tiene como máximo una historia.
- Mascota, cliente, veterinario, fecha y motivo se toman de la cita, para que no puedan contradecirse.
- El diagnóstico es obligatorio.

**Anotaciones** (`AnotacionHistoriaServiceImpl`)
- Requieren historia existente, veterinario existente, descripción y diagnóstico o evolución.
- La fecha la asigna el servidor.

**Fórmulas médicas** (`FormulaServiceImpl`)
- Mascota, cita, veterinario y medicamento deben existir.
- La cita debe pertenecer a la misma mascota de la fórmula.
- La fecha de emisión no puede ser anterior a la fecha de la consulta.
- Dosis y frecuencia son obligatorias. La duración debe ser de al menos un día.
- No hay endpoint de eliminación de fórmulas. Por eso la regla 8 de la especificación (no eliminar físicamente información clínica) se cumple por diseño.

## 5. Decisiones técnicas

- **Validación en backend, no solo en Angular.** Angular muestra mensajes y valida formularios, pero el backend decide siempre. Así se cumple la sección 5 del PDF.
- **Interpretación del margen de 15 minutos.** Se toma como ventana cerrada (±15 min). Dos citas a 15 minutos exactos se consideran en conflicto.
- **Fecha pasada a nivel de día.** Se comparan fechas, no horas. Una cita de hoy a una hora ya pasada sigue siendo válida.
- **Estados como constantes de la entidad `Cita`** (`ESTADOS`), compartidas por los servicios.
- **Historia y anotaciones separadas.** Una historia es una consulta atendida. Sus anotaciones se consultan por historia o por mascota, que es el histórico que pide la sección 3.4.
- **Edición con PUT y creación con POST.** Así se cumple la sección 4 sobre métodos HTTP. Se mantienen las rutas existentes para no romper lo que ya funcionaba.
- **Orden, búsqueda y paginación en el frontend.** Los datos de la clínica son pocos, así que se hacen en memoria con helpers compartidos (`theme/shared/helpers/tabla.ts`) y un componente de paginación reutilizable.
- **Combos dependientes.** Cliente → Mascota → Cita (o Veterinario) se cargan por cascada desde el servicio, sin cargar todo en memoria.
- **Manejo de errores.** `GlobalExceptionHandler` responde 400 para parámetros mal formados y no expone detalles técnicos en los 500.

## 6. Cambios realizados

**Backend**
- Corrección de compilación: `models/CitaRQ.java` y `models/AnotacionHistoriaRQ.java` se renombraron a `CitaRq.java` y `AnotacionHistoriaRq.java`. El nombre de archivo no coincidía con la clase y en Linux no compilaba.
- Entidades: `FormulaMedica` (mascota, veterinario, fecha de emisión, frecuencia, duración), `HistoriaMedica` (rediseñada como consulta 1:1 con cita) y `AnotacionHistoria` (observación, diagnóstico).
- Servicios y endpoints nuevos: historias clínicas, veterinarios, consultas por id de cliente, mascota, cita y fórmula, y la consulta de anotaciones.
- Reglas de negocio de las secciones 1 a 3 del PDF (sección 4 de este documento).
- `BadRequestException` acepta estado HTTP (404 para no encontrado). `GlobalExceptionHandler` añade manejo de parámetros inválidos y oculta los detalles técnicos.
- Corrección: los GET de cliente y mascota tenían `consumes` y fallaban sin `Content-Type`.
- Esquema y datos de prueba en `database/`.
- Pruebas: `CitaServiceImplTest` (3 casos: cruce de horario, citas canceladas e inmodificabilidad de atendidas) y el script `pruebas/pruebas_api.sh` (50 casos de API).

**Frontend**
- Módulo de fórmulas médicas reescrito: listado con búsqueda, filtro por veterinario, orden por columna y paginación, más formulario con combos dependientes y detalle de solo lectura.
- Módulo nuevo de citas: listado, filtro por estado, agendar y editar (con las restricciones de estado).
- Módulo nuevo de historias clínicas: listado, creación desde cita atendida, modal de detalle con histórico de anotaciones (filtro, orden por fecha, paginación y detalle) y agregar anotación.
- Servicios actualizados. Las fórmulas usaban `guardar` y `actualizar` con POST, rutas que el backend no exponía. Ahora usan `crear` (POST) y `actualizar` (PUT).
- Helpers compartidos: `tabla.ts`, `errores.ts` (mensaje de error del backend o aviso de comunicación) y `PaginacionComponent`.
- Rutas y menú para citas e historias.
- `environment.prod.ts` no tenía `apiUrlAuth`, así que el build de producción fallaba. Se corrigió.

## 7. Verificación realizada

- `mvn compile` y `mvn test`: 4 pruebas pasan (3 unitarias y la de contexto).
- Pruebas de API con curl contra MariaDB: 50 de 50 casos correctos.
- Flujos en navegador (Chromium con Playwright) contra el backend real: 13 de 13 correctos. Incluyen agendar, cruce de horario, marcar cita atendida, crear fórmula, crear historia y agregar anotación.
- `ng build` (desarrollo y producción) compila sin errores. `ng lint` reporta 16 errores, todos en archivos que ya existían y no se tocaron.

## 8. Pendientes y limitaciones

- No hay autenticación en las rutas del frontend. Ya era así antes de esta actividad.
- Algunas clases del backend que no se tocaron (`Raza*`, `ClinicaApi`) no tienen Javadoc completo.
- Las entidades se devuelven directamente en JSON, como en el resto del proyecto. Por eso las respuestas incluyen objetos relacionados.
- La paginación y el filtrado son en el cliente. Con mucha más información convendría moverlos al servidor.
