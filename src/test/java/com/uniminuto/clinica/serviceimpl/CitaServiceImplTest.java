package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Cliente;
import com.uniminuto.clinica.entity.Mascota;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.CitaRq;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.ClienteRepository;
import com.uniminuto.clinica.repository.MascotaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas de las reglas de cruces de horario y de estados de las citas.
 */
@ExtendWith(MockitoExtension.class)
class CitaServiceImplTest {

    @InjectMocks
    private CitaServiceImpl service;

    @Mock
    private CitaRepository citaRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private MascotaRepository mascotaRepository;
    @Mock
    private MedicoRepository medicoRepository;

    private final Cliente cliente = cliente();
    private final Mascota mascota = mascota();
    private final Medico medico = medico();

    @Test
    void crearCita_rechazaCuandoElVeterinarioTieneOtraCitaActivaEnElMargen() {
        LocalDateTime fecha = manana(10, 0);
        preparaRelaciones();
        when(citaRepository.findByMedicoAndFechaHoraBetween(any(), any(), any()))
                .thenReturn(List.of(cita(99L, fecha.plusMinutes(10), Cita.ESTADO_CONFIRMADA)));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.crearCita(rq(fecha)));

        assertEquals("El veterinario ya tiene una cita en ese horario. Debe haber al menos 15 minutos entre citas.",
                ex.getMessage());
    }

    @Test
    void crearCita_ignoraCitasCanceladasAlValidarElHorario() {
        LocalDateTime fecha = manana(10, 0);
        preparaRelaciones();
        when(citaRepository.findByMedicoAndFechaHoraBetween(any(), any(), any()))
                .thenReturn(List.of(cita(99L, fecha, Cita.ESTADO_CANCELADA)));
        when(citaRepository.findByMascotaAndFechaHoraBetween(any(), any(), any())).thenReturn(List.of());

        service.crearCita(rq(fecha));

        verify(citaRepository).save(any(Cita.class));
    }

    @Test
    void actualizarCita_noModificaUnaCitaAtendida() {
        Cita atendida = cita(5L, manana(9, 0), Cita.ESTADO_ATENDIDA);
        when(citaRepository.findById(5L)).thenReturn(Optional.of(atendida));
        CitaRq rq = rq(manana(11, 0));
        rq.setId(5L);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.actualizarCita(rq));

        assertEquals("La cita ya fue atendida y su información no puede modificarse", ex.getMessage());
    }

    /** Configura cliente, mascota y veterinario existentes y coherentes entre sí. */
    private void preparaRelaciones() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(mascotaRepository.findById(1)).thenReturn(Optional.of(mascota));
        when(medicoRepository.findById(2L)).thenReturn(Optional.of(medico));
    }

    private CitaRq rq(LocalDateTime fecha) {
        CitaRq rq = new CitaRq();
        rq.setClienteId(1L);
        rq.setMascotaId(1);
        rq.setMedicoId(2L);
        rq.setFechaHora(fecha);
        rq.setMotivo("Control");
        rq.setEstado(Cita.ESTADO_PENDIENTE);
        return rq;
    }

    private static LocalDateTime manana(int hora, int minuto) {
        return LocalDateTime.now().plusDays(1).withHour(hora).withMinute(minuto).withSecond(0).withNano(0);
    }

    private static Cita cita(Long id, LocalDateTime fecha, String estado) {
        Cita cita = new Cita();
        cita.setId(id);
        cita.setFechaHora(fecha);
        cita.setEstado(estado);
        return cita;
    }

    private static Cliente cliente() {
        Cliente c = new Cliente();
        c.setId(1L);
        return c;
    }

    private static Mascota mascota() {
        Mascota m = new Mascota();
        m.setMascotaId(1);
        m.setCliente(cliente());
        return m;
    }

    private static Medico medico() {
        Medico m = new Medico();
        m.setId(2L);
        return m;
    }
}
