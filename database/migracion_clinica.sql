-- Migración de la base clinica (MySQL 8) para el backend de la rama 80869_BRANDON.
-- Ejecutar UNA sola vez, después de importar clinica.sql:
--   mysql -h 127.0.0.1 -u root -p clinica < database/migracion_clinica.sql
-- Las filas con id > 0 en los UPDATE permiten ejecutarlo también con "safe updates" (MySQL Workbench).

-- 1. Estados de cita en mayúsculas: la aplicación usa PENDIENTE, CONFIRMADA, ATENDIDA, CANCELADA y PROGRAMADA.
UPDATE cita SET estado = UPPER(estado) WHERE id > 0;

-- 2. Fórmulas médicas: mascota, veterinario, fecha de emisión, frecuencia y duración.
ALTER TABLE formula_medica
  ADD COLUMN mascota_id INT NULL AFTER cita_id,
  ADD COLUMN medico_id INT NULL AFTER mascota_id,
  ADD COLUMN fecha_emision DATE NULL AFTER medicamento_id,
  ADD COLUMN frecuencia VARCHAR(100) NULL AFTER dosis,
  ADD COLUMN duracion_dias INT NULL AFTER frecuencia;

-- Las fórmulas existentes toman mascota y veterinario de su cita, y la fecha de creación como fecha de emisión.
UPDATE formula_medica f
  JOIN cita c ON c.id = f.cita_id
  SET f.mascota_id = c.mascota_id,
      f.medico_id = c.medico_id,
      f.fecha_emision = DATE(f.fecha_creacion_registro)
  WHERE f.id > 0;

ALTER TABLE formula_medica
  MODIFY mascota_id INT NOT NULL,
  MODIFY medico_id INT NOT NULL,
  MODIFY fecha_emision DATE NOT NULL;

-- 3. Historias clínicas: una por cita atendida. La tabla está vacía en esta base, por eso las columnas son NOT NULL.
ALTER TABLE historia_medica
  ADD COLUMN cita_id BIGINT UNSIGNED NOT NULL AFTER id,
  ADD COLUMN medico_id INT NOT NULL AFTER paciente_id,
  ADD COLUMN fecha_hora DATETIME NOT NULL AFTER medico_id,
  ADD COLUMN motivo TEXT NULL AFTER fecha_hora,
  ADD COLUMN diagnostico TEXT NOT NULL AFTER motivo,
  ADD COLUMN observaciones TEXT NULL AFTER diagnostico,
  ADD UNIQUE KEY uk_historia_cita (cita_id);

-- 4. Anotaciones: observación y diagnóstico o evolución. La tabla está vacía en esta base.
ALTER TABLE anotacion_historia
  ADD COLUMN observacion TEXT NULL AFTER descripcion,
  ADD COLUMN diagnostico TEXT NOT NULL AFTER observacion;
