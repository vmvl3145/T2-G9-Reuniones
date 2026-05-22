package reuniones.gestion;

import reuniones.participantes.Empleado;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class ReunionPresencial extends Reunion {
    private String sala;

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