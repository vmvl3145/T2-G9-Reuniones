package reuniones.participantes;

/** Representa a una persona ajena a la empresa que participará en una reunión.
 * Implementa la interfaz Invitable para ser tratado de la misma forma que un Empleado.*/
public class InvitadoExterno implements Invitable {
    private String nombre;
    private String apellidos;
    private String correo;

    /**
     * Crea un nuevo invitado externo al sistema
     * @param nombre Nombre del invitado
     * @param apellidos Apellidos del invitado.
     * @param correo Correo electrónico de contacto del invitado */
    public InvitadoExterno(String nombre, String apellidos, String correo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
    }

    /** Simula el envío de una invitación a la reunión al correo del invitado externo. */
    @Override
    public void invitar() {
        System.out.println("Invitación enviada al correo externo de: " + correo);
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos + " (Invitado Externo)";
    }
}