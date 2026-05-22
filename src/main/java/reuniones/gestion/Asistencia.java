package reuniones.gestion;

import reuniones.participantes.Invitable;

public class Asistencia {
    private Invitable participante;

    public Asistencia(Invitable participante) {
        this.participante = participante;
    }

    public Invitable getParticipante() { return participante; }
}