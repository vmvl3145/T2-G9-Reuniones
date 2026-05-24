package reuniones.gestion;

import reuniones.participantes.Invitable;
import java.time.Instant;

/** Representa el acto de convocar a un participante a la reunión, registrando la hora de envío. */
public class Invitacion {
    private Instant hora;
    private Invitable invitado;

    /** Genera una invitación y notifica al invitado automáticamente.
     * @param invitado La persona a invitar.
     * @param hora El momento exacto en que se generó la invitación. */
    public Invitacion(Invitable invitado, Instant hora) {
        this.invitado = invitado;
        this.hora = hora;
        this.invitado.invitar();
    }

    public Instant getHora() { return hora; }
    public Invitable getInvitado() { return invitado; }
}