package reuniones.excepciones;

//excepcion para cuando se intenta finalizar o registrar asistencia en reunion que no ha sido iniciada.
public class ReunionNoIniciadaException extends Exception {
    public ReunionNoIniciadaException(String mensaje) {
        super(mensaje);
    }
}