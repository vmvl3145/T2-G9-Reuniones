package reuniones.excepciones;

//para cuando se intenta realizar una operacion en una reunion que ya ha sido finalizada.
public class ReunionYaFinalizadaException extends Exception {
    public ReunionYaFinalizadaException(String mensaje) {
        super(mensaje);
    }
}