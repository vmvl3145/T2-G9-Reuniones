package reuniones.gestion;

import reuniones.participantes.Empleado;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class ReunionVirtual extends Reunion {
    private String enlace;

    public ReunionVirtual(Date fecha, Instant horaAgendada, Duration duracionAgendada, TipoReunion tipoReunion, Empleado organizador, String enlace) {
        super(fecha, horaAgendada, duracionAgendada, tipoReunion, organizador);
        this.enlace = enlace;
    }

    public String getEnlace() {
        return enlace;
    }

    @Override
    public String toString() {
        return "Reunion Virtual (Enlace: " + enlace + ") - Tipo de Reunión: " + super.getTipoReunion();
    }
}
