package reuniones.participantes;

/** Define el comportamiento y datos básicos de cualquier persona (interna o externa) que pueda ser invitada a una reunión. */
public interface Invitable {

    void invitar();
    String getNombre();
    /** @return El nombre del participante. */
    String getApellidos();
    /** @return Los apellidos del participante. */
    String getCorreo();
    /** @return El correo electrónico del participante. */
}