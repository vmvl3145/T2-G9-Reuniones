package reuniones.gestion;

import reuniones.participantes.Empleado;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/** Representa una reunión que se lleva a cabo físicamente en una sala de la empresa. */
public class ReunionPresencial extends Reunion {
    private String sala;

    /** Crea una nueva reunión presencial.
     * @param fecha Día de la reunión.
     * @param horaPrevista Momento planificado de inicio.
     * @param duracionPrevista Tiempo estimado de duración.
     * @param tipoReunion Categoría de la reunión.
     * @param organizador Empleado a cargo.
     * @param sala Identificador del espacio físico. */
    public ReunionPresencial(Date fecha, Instant horaPrevista, Duration duracionPrevista, TipoReunion tipoReunion, Empleado organizador, String sala) {
        super(fecha, horaPrevista, duracionPrevista, tipoReunion, organizador);
        this.sala = sala;
    }

    public String getSala() {
        return sala;
    }

    @Override
    public String toString() {
        return "Reunión Presencial (Sala: " + sala + ") - Tipo de Reunión: " + super.getTipoReunion();
    }
}