package reuniones.gestion;

import reuniones.participantes.Empleado;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class ReunionPresencial extends Reunion {
    private String sala;

    public ReunionPresencial(Date fecha, Instant horaAgendada, Duration duracionAgendada, TipoReunion tipoReunion, Empleado organizador, String enlace) {
        super(fecha, horaAgendada, duracionAgendada, tipoReunion, organizador);
        this.sala = sala;
    }

    public String getSala() {
        return sala;
    }

    @Override
    public String toString() {
        return "Reunion Presencial (Enlace: " + sala + ") - Tipo de Reunión: " + super.getTipoReunion();
    }
}