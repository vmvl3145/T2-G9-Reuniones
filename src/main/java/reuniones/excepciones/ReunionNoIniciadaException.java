package reuniones.excepciones;

/** Excepción lanzada cuando se intenta realizar una operación (como finalizar o registrar asistencia) en una reunión que aún no ha marcado su hora de inicio. */
public class ReunionNoIniciadaException extends Exception {
    /** Crea una nueva excepción indicando que la reunión no ha comenzado
     * @param mensaje El detalle del error */
    public ReunionNoIniciadaException(String mensaje) {
        super(mensaje);
    }
}