package reuniones.excepciones;

/** Excepción lanzada cuando se intenta registrar la asistencia de una persona que no se encuentra en la lista de invitados de la reunión */
public class EmpleadoNoInvitadoException extends Exception {
    /** Crea una nueva excepción indicando que el participante no fue invitado.
     * @param mensaje El detalle del error */
    public EmpleadoNoInvitadoException(String mensaje) {
        super(mensaje);
    }
}