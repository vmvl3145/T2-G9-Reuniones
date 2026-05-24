package reuniones.gestion;

import reuniones.participantes.Invitable;

/** Representa el registro validado de que un participante convocado efectivamente se presentó a la reunión. */
public class Asistencia {
    private Invitable participante;

    /** Crea un nuevo registro de asistencia para un participante.
     * @param participante La persona (Empleado o InvitadoExterno) que asistió a la reunión. */
    public Asistencia(Invitable participante) {
        this.participante = participante;
    }

    /**
     * Obtiene el participante asociado a este registro de asistencia.
     * @return El objeto Invitable que representa al participante */
    public Invitable getParticipante() { return participante; }
}