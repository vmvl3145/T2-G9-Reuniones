package reuniones.gestion;

import java.time.Instant;

/** Representa el retraso de un participante en una reunion.
 * Se registra cuando una persona marca su asistencia despues de la hora agendada. */
public class Retraso {
    private Instant hora;

    /** Crea un registro de retraso con la hora real de llegada del participante.
     * @param hora La hora exacta en la que el participante se unio a la reunion. */
    public Retraso(Instant hora) {
        this.hora = hora;
    }

    public Instant getHora() { return hora; }
    public void setHora(Instant hora) { this.hora = hora; }

    @Override
    public String toString() {
        return "Retraso registrado a las: " + hora;
    }
}