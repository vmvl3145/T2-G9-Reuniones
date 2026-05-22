package reuniones.participantes;

public class InvitadoExterno implements Invitable {
    private String nombre;
    private String apellidos;
    private String correo;

    public InvitadoExterno(String nombre, String apellidos, String correo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
    }

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