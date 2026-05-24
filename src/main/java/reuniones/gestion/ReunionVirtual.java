package reuniones.gestion;

import reuniones.participantes.Empleado;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/** Representa una reunión que se lleva a cabo a través de una plataforma online */
public class ReunionVirtual extends Reunion {
    private String enlace;

    /** Crea una nueva reunión virtual.
     * @param fecha Día de la reunión.
     * @param horaAgendada Momento planificado de inicio.
     * @param duracionAgendada Tiempo estimado de duración.
     * @param tipoReunion Categoría de la reunión.
     * @param organizador Empleado a cargo.
     * @param enlace URL para conectarse a la llamada */
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
