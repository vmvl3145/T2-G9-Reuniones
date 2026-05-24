package reuniones.excepciones;

/** Excepción lanzada cuando se intenta realizar una operación (como registrar asistencia o reiniciar) en una reunión que ya ha marcado su hora de fin. */
public class ReunionYaFinalizadaException extends Exception {
    /** Crea una nueva excepción indicando que la reunión ya concluyó.
     * @param mensaje El detalle del error. */
    public ReunionYaFinalizadaException(String mensaje) {
        super(mensaje);
    }
}