#!/usr/bin/env bash
# Requiere el backend en http://localhost:8080 y la base cargada con database/datos_prueba.sql (ejecutar sobre base recién cargada).
# Recorre los servicios y reglas de negocio. Imprime código HTTP y cuerpo resumido.
BASE="http://localhost:8080/clinica/v1"
JSON='Content-Type: application/json'
MANANA=$(date -d "+1 day" +%F)
PASADO=$(date -d "+2 day" +%F)
ok=0; fail=0
check() { # $1=descripcion $2=esperado $3=respuesta_http $4=cuerpo
  if [ "$2" = "$3" ]; then ok=$((ok+1)); r="OK  "; else fail=$((fail+1)); r="FAIL"; fi
  echo "$r [$3 esperado $2] $1 :: $(echo "$4" | cut -c1-160)"
}
req() { # $1=metodo $2=ruta $3=body(opcional)
  local out
  if [ -n "$3" ]; then out=$(curl -s -w '\n%{http_code}' -X "$1" "$BASE$2" -H "$JSON" -d "$3");
  else out=$(curl -s -w '\n%{http_code}' -X "$1" "$BASE$2"); fi
  HTTP=$(echo "$out" | tail -n1); BODY=$(echo "$out" | sed '$d')
}
echo "== Clientes y mascotas"
req GET /cliente/listar;            check "listar clientes" 200 $HTTP "$BODY"
req GET /cliente/1;                 check "consultar cliente 1" 200 $HTTP "$BODY"
req GET /cliente/abc;               check "id no numérico" 400 $HTTP "$BODY"
req GET /cliente/999;               check "cliente inexistente" 404 $HTTP "$BODY"
req GET "/mascota/buscar-by-cliente?clienteId=1"; check "mascotas del cliente 1" 200 $HTTP "$BODY"
req GET /mascota/1;                 check "consultar mascota 1" 200 $HTTP "$BODY"
req GET /mascota/999;               check "mascota inexistente" 404 $HTTP "$BODY"
echo "== Veterinarios y medicamentos"
req GET /medico/listar;             check "listar veterinarios" 200 $HTTP "$BODY"
req GET /formula-medica/medicamentos; check "listar medicamentos" 200 $HTTP "$BODY"
echo "== Citas"
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":1,\"medicoId\":1,\"fechaHora\":\"${MANANA}T10:00:00\",\"motivo\":\"Vacunación\",\"estado\":\"PENDIENTE\"}"; check "crear cita A (mascota1, vet1, 10:00)" 200 $HTTP "$BODY"
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":2,\"medicoId\":1,\"fechaHora\":\"${MANANA}T10:10:00\",\"motivo\":\"Control\",\"estado\":\"PENDIENTE\"}"; check "vet ocupado a 10:10 (menos de 15 min)" 400 $HTTP "$BODY"
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":3,\"medicoId\":2,\"fechaHora\":\"${MANANA}T10:00:00\",\"motivo\":\"Control\",\"estado\":\"PENDIENTE\"}"; check "mascota 3 no pertenece al cliente 1" 400 $HTTP "$BODY"
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":1,\"medicoId\":2,\"fechaHora\":\"${MANANA}T10:05:00\",\"motivo\":\"Control\",\"estado\":\"PENDIENTE\"}"; check "misma mascota a 10:05 (cruce)" 400 $HTTP "$BODY"
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":1,\"medicoId\":1,\"fechaHora\":\"2020-01-01T10:00:00\",\"motivo\":\"Antigua\",\"estado\":\"PENDIENTE\"}"; check "fecha pasada" 400 $HTTP "$BODY"
req POST /cita/crear "{\"clienteId\":9,\"mascotaId\":1,\"medicoId\":1,\"fechaHora\":\"${MANANA}T12:00:00\",\"motivo\":\"X\",\"estado\":\"PENDIENTE\"}"; check "cliente inexistente" 404 $HTTP "$BODY"
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":1,\"medicoId\":1,\"fechaHora\":\"${MANANA}T12:00:00\",\"motivo\":\"X\",\"estado\":\"INVENTADO\"}"; check "estado no válido" 400 $HTTP "$BODY"
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":2,\"medicoId\":2,\"fechaHora\":\"${MANANA}T10:20:00\",\"motivo\":\"Control\",\"estado\":\"CONFIRMADA\"}"; check "cita B a 10:20 (mascota2, vet2) sin cruce" 200 $HTTP "$BODY"
req POST /cita/crear "{\"clienteId\":2,\"mascotaId\":3,\"medicoId\":2,\"fechaHora\":\"${PASADO}T09:00:00\",\"motivo\":\"Cojera\",\"estado\":\"PENDIENTE\"}"; check "cita C (mascota3, cliente2)" 200 $HTTP "$BODY"
req GET /cita/listar; check "listar citas" 200 $HTTP "$BODY"
echo "   orden desc: $(echo "$BODY" | grep -o '"fechaHora":"[^"]*"' | tr '\n' ' ')"
req GET /cita/1; check "consultar cita 1" 200 $HTTP "$BODY"
req GET /cita/999; check "cita inexistente" 404 $HTTP "$BODY"
req PUT /cita/actualizar "{\"id\":1,\"clienteId\":1,\"mascotaId\":1,\"medicoId\":1,\"fechaHora\":\"${MANANA}T11:00:00\",\"motivo\":\"Vacunación anual\",\"estado\":\"CONFIRMADA\"}"; check "editar cita 1 (cambia hora a 11:00)" 200 $HTTP "$BODY"
req PUT /cita/actualizar "{\"id\":1,\"clienteId\":1,\"mascotaId\":1,\"medicoId\":1,\"fechaHora\":\"${MANANA}T11:00:00\",\"motivo\":\"Vacunación anual\",\"estado\":\"ATENDIDA\"}"; check "marcar cita 1 como atendida" 200 $HTTP "$BODY"
req PUT /cita/actualizar "{\"id\":1,\"clienteId\":1,\"mascotaId\":1,\"medicoId\":1,\"fechaHora\":\"${PASADO}T11:00:00\",\"motivo\":\"Cambio\",\"estado\":\"CONFIRMADA\"}"; check "editar cita atendida" 400 $HTTP "$BODY"
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":2,\"medicoId\":1,\"fechaHora\":\"${PASADO}T15:00:00\",\"motivo\":\"Para cancelar\",\"estado\":\"PENDIENTE\"}"; check "cita D para cancelar" 200 $HTTP "$BODY"
req PUT /cita/actualizar "{\"id\":4,\"clienteId\":1,\"mascotaId\":2,\"medicoId\":1,\"fechaHora\":\"${PASADO}T15:00:00\",\"motivo\":\"Para cancelar\",\"estado\":\"CANCELADA\"}"; check "cancelar cita 4" 200 $HTTP "$BODY"
req PUT /cita/actualizar "{\"id\":4,\"clienteId\":1,\"mascotaId\":2,\"medicoId\":1,\"fechaHora\":\"${PASADO}T15:00:00\",\"motivo\":\"Cambio\",\"estado\":\"CANCELADA\"}"; check "editar cancelada sin reactivar" 400 $HTTP "$BODY"
req PUT /cita/actualizar "{\"id\":4,\"clienteId\":1,\"mascotaId\":2,\"medicoId\":1,\"fechaHora\":\"${MANANA}T15:00:00\",\"motivo\":\"Reactivada\",\"estado\":\"PENDIENTE\"}"; check "reactivar cita 4 (estado explícito)" 200 $HTTP "$BODY"
echo "== Fórmulas médicas"
F="\"citaId\":1,\"mascotaId\":1,\"medicoId\":1,\"medicamentoId\":1,\"fechaEmision\":\"${MANANA}\",\"dosis\":\"2 ml\",\"frecuencia\":\"cada 12 horas\",\"duracionDias\":7,\"indicaciones\":\"Con comida\""
req POST /formula-medica/crear "{$F}"; check "crear fórmula válida" 200 $HTTP "$BODY"
req POST /formula-medica/crear "{\"citaId\":1,\"mascotaId\":2,\"medicoId\":1,\"medicamentoId\":1,\"fechaEmision\":\"${MANANA}\",\"dosis\":\"2 ml\",\"frecuencia\":\"x\",\"duracionDias\":7}"; check "mascota distinta a la de la cita" 400 $HTTP "$BODY"
req POST /formula-medica/crear "{\"citaId\":1,\"mascotaId\":1,\"medicoId\":1,\"medicamentoId\":1,\"fechaEmision\":\"2020-01-01\",\"dosis\":\"2 ml\",\"frecuencia\":\"x\",\"duracionDias\":7}"; check "emisión anterior a la consulta" 400 $HTTP "$BODY"
req POST /formula-medica/crear "{\"citaId\":1,\"mascotaId\":1,\"medicoId\":1,\"medicamentoId\":1,\"fechaEmision\":\"${MANANA}\",\"dosis\":\"\",\"frecuencia\":\"x\",\"duracionDias\":7}"; check "dosis vacía" 400 $HTTP "$BODY"
req POST /formula-medica/crear "{\"citaId\":1,\"mascotaId\":1,\"medicoId\":1,\"medicamentoId\":1,\"fechaEmision\":\"${MANANA}\",\"dosis\":\"2 ml\",\"frecuencia\":\"x\",\"duracionDias\":0}"; check "duración cero" 400 $HTTP "$BODY"
req POST /formula-medica/crear "{\"citaId\":1,\"mascotaId\":999,\"medicoId\":1,\"medicamentoId\":1,\"fechaEmision\":\"${MANANA}\",\"dosis\":\"2 ml\",\"frecuencia\":\"x\",\"duracionDias\":7}"; check "mascota inexistente" 404 $HTTP "$BODY"
req POST /formula-medica/crear "{\"citaId\":1,\"mascotaId\":1,\"medicoId\":99,\"medicamentoId\":1,\"fechaEmision\":\"${MANANA}\",\"dosis\":\"2 ml\",\"frecuencia\":\"x\",\"duracionDias\":7}"; check "veterinario inexistente" 404 $HTTP "$BODY"
req PUT /formula-medica/actualizar "{\"id\":1,\"citaId\":1,\"mascotaId\":1,\"medicoId\":2,\"medicamentoId\":2,\"fechaEmision\":\"${MANANA}\",\"dosis\":\"1 ml\",\"frecuencia\":\"cada 24 horas\",\"duracionDias\":5,\"indicaciones\":\"Actualizada\"}"; check "editar fórmula 1" 200 $HTTP "$BODY"
req GET /formula-medica/1; check "consultar fórmula 1" 200 $HTTP "$BODY"
req GET /formula-medica/77; check "fórmula inexistente" 404 $HTTP "$BODY"
req GET /formula-medica/listar; check "listar fórmulas" 200 $HTTP "$BODY"
echo "== Historias clínicas y anotaciones"
req POST /historia-medica/crear '{"citaId":2,"diagnostico":"Sano"}'; check "historia de cita no atendida" 400 $HTTP "$BODY"
req POST /historia-medica/crear '{"citaId":1,"diagnostico":""}'; check "historia sin diagnóstico" 400 $HTTP "$BODY"
req POST /historia-medica/crear '{"citaId":1,"diagnostico":"Vacunación completa","observaciones":"Sin reacciones"}'; check "historia de cita atendida" 200 $HTTP "$BODY"
req POST /historia-medica/crear '{"citaId":1,"diagnostico":"Duplicada"}'; check "historia duplicada para la misma cita" 400 $HTTP "$BODY"
req GET /historia-medica/listar; check "listar historias" 200 $HTTP "$BODY"
req POST /anotacion-historia/crear '{"historiaId":1,"medicoId":2,"descripcion":"Control de peso","observacion":"Estable","diagnostico":"Evolución favorable"}'; check "agregar anotación" 200 $HTTP "$BODY"
req POST /anotacion-historia/crear '{"historiaId":1,"medicoId":2,"descripcion":"Sin diagnóstico"}'; check "anotación sin diagnóstico" 400 $HTTP "$BODY"
req POST /anotacion-historia/crear '{"historiaId":77,"medicoId":2,"descripcion":"x","diagnostico":"y"}'; check "historia inexistente" 404 $HTTP "$BODY"
req GET "/anotacion-historia/listar?historiaId=1"; check "anotaciones por historia" 200 $HTTP "$BODY"
req GET "/anotacion-historia/listar?mascotaId=1"; check "anotaciones por mascota" 200 $HTTP "$BODY"
req GET "/anotacion-historia/listar"; check "anotaciones sin filtro" 400 $HTTP "$BODY"
echo "----"
echo "RESULTADO: $ok correctas, $fail fallidas"
