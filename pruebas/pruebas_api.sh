#!/usr/bin/env bash
# Verifica los servicios contra la base clinica migrada (database/clinica.sql + migracion_clinica.sql).
# Requiere el backend en http://localhost:8080 (ver docs/Documento_entrega.md). Usa los ids de la base:
# cliente 1, mascota 1 (de cliente 1), mascota 2 (de cliente 2), veterinarios 1 y 2, medicamento 2, citas 1 y 2.
# Cada ejecución crea sus propios registros con una fecha futura y un motivo único, así que se puede repetir.
B=http://localhost:8080/clinica/v1
J='Content-Type: application/json'
ETIQUETA="prueba-$(date +%s)"
FECHA=$(date -d "+$((400 + RANDOM % 300)) day" +%F)
ok=0; fail=0

req() { # $1 método, $2 ruta, $3 cuerpo (opcional) -> CODE y BODY
  local out
  if [ -n "$3" ]; then out=$(curl -s -w '\n%{http_code}' -X "$1" "$B$2" -H "$J" -d "$3")
  else out=$(curl -s -w '\n%{http_code}' -X "$1" "$B$2"); fi
  CODE=$(echo "$out" | tail -n1); BODY=$(echo "$out" | sed '$d')
}
check() { # $1 descripción, $2 esperado
  if [ "$CODE" = "$2" ]; then ok=$((ok+1)); echo "OK   [$CODE] $1"
  else fail=$((fail+1)); echo "FAIL [$CODE, esperado $2] $1 :: $(echo "$BODY" | head -c 160)"; fi
}
campo() { # $1 expresión python sobre la variable d (JSON de BODY)
  echo "$BODY" | python3 -c "import sys,json; d=json.load(sys.stdin); print($1)"
}

echo "== Lecturas"
for r in /cliente/listar /cliente/1 /mascota/1 "/mascota/buscar-by-cliente?clienteId=1" /medico/listar \
         /cita/listar /cita/1 /formula-medica/listar /formula-medica/1 /formula-medica/medicamentos \
         /historia-medica/listar; do
  req GET "$r"; check "GET $r" 200
done
req GET /cliente/999; check "cliente inexistente" 404
req GET /cliente/abc; check "id no numérico" 400

echo "== Citas"
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":1,\"medicoId\":1,\"fechaHora\":\"${FECHA}T10:00:00\",\"motivo\":\"$ETIQUETA\",\"estado\":\"PENDIENTE\"}"
check "agendar cita nueva" 200
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":2,\"medicoId\":1,\"fechaHora\":\"${FECHA}T10:05:00\",\"motivo\":\"cruce\",\"estado\":\"PENDIENTE\"}"
check "veterinario con cita a 5 minutos rechazado" 400
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":3,\"medicoId\":2,\"fechaHora\":\"${FECHA}T11:00:00\",\"motivo\":\"x\",\"estado\":\"PENDIENTE\"}"
check "mascota 3 no pertenece al cliente 1" 400
req POST /cita/crear "{\"clienteId\":1,\"mascotaId\":1,\"medicoId\":2,\"fechaHora\":\"2020-01-01T10:00:00\",\"motivo\":\"x\",\"estado\":\"PENDIENTE\"}"
check "fecha pasada rechazada" 400
req GET /cita/listar
CITA=$(echo "$BODY" | python3 -c "import sys,json; print([c['id'] for c in json.load(sys.stdin) if c['motivo']=='$ETIQUETA'][0])")
req PUT /cita/actualizar "{\"id\":$CITA,\"clienteId\":1,\"mascotaId\":1,\"medicoId\":1,\"fechaHora\":\"${FECHA}T10:00:00\",\"motivo\":\"$ETIQUETA\",\"estado\":\"ATENDIDA\"}"
check "marcar cita $CITA como ATENDIDA" 200
req PUT /cita/actualizar "{\"id\":$CITA,\"clienteId\":1,\"mascotaId\":1,\"medicoId\":1,\"fechaHora\":\"${FECHA}T12:00:00\",\"motivo\":\"cambio\",\"estado\":\"CONFIRMADA\"}"
check "editar cita atendida rechazado" 400

echo "== Historias y anotaciones"
req POST /historia-medica/crear "{\"citaId\":$CITA,\"diagnostico\":\"Sano\",\"observaciones\":\"$ETIQUETA\"}"
check "historia desde cita atendida" 200
req POST /historia-medica/crear "{\"citaId\":$CITA,\"diagnostico\":\"Duplicada\"}"
check "segunda historia para la misma cita rechazada" 400
req POST /historia-medica/crear "{\"citaId\":2,\"diagnostico\":\"Cita 2 no atendida\"}"
check "historia desde cita no atendida rechazada" 400
req GET /historia-medica/listar
HIST=$(echo "$BODY" | python3 -c "import sys,json; print([h['id'] for h in json.load(sys.stdin) if h['observaciones']=='$ETIQUETA'][0])")
req POST /anotacion-historia/crear "{\"historiaId\":$HIST,\"medicoId\":2,\"descripcion\":\"Control\",\"observacion\":\"Estable\",\"diagnostico\":\"Favorable\"}"
check "agregar anotación a la historia $HIST" 200
req POST /anotacion-historia/crear "{\"historiaId\":$HIST,\"medicoId\":2,\"descripcion\":\"sin diagnóstico\"}"
check "anotación sin diagnóstico rechazada" 400
req GET "/anotacion-historia/listar?historiaId=$HIST"
check "anotaciones de la historia" 200
req GET "/anotacion-historia/listar"
check "anotaciones sin filtro rechazadas" 400

echo "== Fórmulas médicas"
req POST /formula-medica/crear "{\"citaId\":1,\"mascotaId\":1,\"medicoId\":1,\"medicamentoId\":2,\"fechaEmision\":\"$FECHA\",\"dosis\":\"1 tableta\",\"frecuencia\":\"cada 12 horas\",\"duracionDias\":5}"
check "crear fórmula para cita 1" 200
req POST /formula-medica/crear "{\"citaId\":2,\"mascotaId\":1,\"medicoId\":1,\"medicamentoId\":2,\"fechaEmision\":\"$FECHA\",\"dosis\":\"x\",\"frecuencia\":\"x\",\"duracionDias\":1}"
check "cita de otra mascota rechazada" 400
req POST /formula-medica/crear "{\"citaId\":1,\"mascotaId\":1,\"medicoId\":1,\"medicamentoId\":2,\"fechaEmision\":\"2020-01-01\",\"dosis\":\"x\",\"frecuencia\":\"x\",\"duracionDias\":1}"
check "emisión anterior a la consulta rechazada" 400
req POST /formula-medica/crear "{\"citaId\":1,\"mascotaId\":1,\"medicoId\":1,\"medicamentoId\":2,\"fechaEmision\":\"$FECHA\",\"dosis\":\"x\",\"frecuencia\":\"x\",\"duracionDias\":0}"
check "duración cero rechazada" 400
req PUT /formula-medica/actualizar "{\"id\":1,\"citaId\":1,\"mascotaId\":1,\"medicoId\":1,\"medicamentoId\":2,\"fechaEmision\":\"2025-10-20\",\"dosis\":\"800 mg\",\"frecuencia\":\"cada 12 horas\",\"duracionDias\":7,\"indicaciones\":\"Tomar después de las comidas\"}"
check "completar fórmula 1 (datos migrados)" 200

echo "----"
echo "RESULTADO: $ok correctas, $fail fallidas"
[ "$fail" -eq 0 ]
