package reuniones.gestion;

import reuniones.participantes.Invitable;
import java.time.Instant;

public class Invitacion {
    private Instant hora;
    private Invitable invitado;

    public Invitacion(Invitable invitado, Instant hora) {
        this.invitado = invitado;
        this.hora = hora;
        this.invitado.invitar();
    }

    public Instant getHora() { return hora; }
    public Invitable getInvitado() { return invitado; }
}