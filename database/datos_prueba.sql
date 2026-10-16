-- Datos de prueba para la base de datos clinica.
-- Ejecutar después de crear el esquema (ver clinica.sql).
-- Cargar con codificación UTF-8, por ejemplo:
--   mariadb --default-character-set=utf8mb4 -u root -p clinica < database/datos_prueba.sql
USE clinica;

INSERT INTO raza (raza_id, nombre, especie, fecha_creacion) VALUES
  (1, 'Labrador', 'Perro', NOW()),
  (2, 'Siamés', 'Gato', NOW());

INSERT INTO cliente (cliente_id, tipo_documento, numero_documento, nombres, apellidos, fecha_nacimiento, genero, telefono, direccion, activo) VALUES
  (1, 'CC', '1001001', 'Ana', 'Gómez', '1990-05-10', 'F', '3001112233', 'Calle 1 # 2-3', 1),
  (2, 'CC', '1001002', 'Luis', 'Pérez', '1985-11-22', 'M', '3004445566', 'Carrera 5 # 6-7', 1);

INSERT INTO mascota (mascota_id, nombre_mascota, edad, fecha_registro, cliente_id, raza_id) VALUES
  (1, 'Firulais', 4, NOW(), 1, 1),
  (2, 'Michi', 2, NOW(), 1, 2),
  (3, 'Rocky', 6, NOW(), 2, 1);

INSERT INTO medico (id, tipo_documento, numero_documento, nombres, apellidos, telefono, registro_profesional, especializacion_id) VALUES
  (1, 'CC', '80001', 'Carlos', 'Ruiz', '3100000001', 'MP-1001', 1),
  (2, 'CC', '80002', 'María', 'Torres', '3100000002', 'MP-1002', 2);

INSERT INTO medicamento (id, nombre, descripcion, presentacion, fecha_compra, fecha_vence, fecha_creacion_registro) VALUES
  (1, 'Amoxicilina', 'Antibiótico de amplio espectro', 'Suspensión 250 mg/5 ml', '2026-01-10', '2027-01-10', NOW()),
  (2, 'Meloxicam', 'Antiinflamatorio no esteroideo', 'Tableta 1.5 mg', '2026-02-01', '2027-02-01', NOW()),
  (3, 'Vitamina B12', 'Suplemento vitamínico', 'Inyectable 1 mg/ml', '2026-03-05', '2027-03-05', NOW());
