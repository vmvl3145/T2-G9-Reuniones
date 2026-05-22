package reuniones.excepciones;

//por si se registra asistencia de persona que no fue invitada a la reunion
public class EmpleadoNoInvitadoException extends Exception {
    public EmpleadoNoInvitadoException(String mensaje) {
        super(mensaje);
    }
}