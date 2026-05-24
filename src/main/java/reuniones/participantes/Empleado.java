package reuniones.participantes;

/** Representa a un trabajador interno de la empresa.
 * Implementa la interfaz Invitable para poder ser convocado a las reuniones. */
public class Empleado implements Invitable {
    private String id;
    private String apellidos;
    private String nombre;
    private String correo;
    private Departamento departamento;

    /**Crea un nuevo empleado y lo asocia automáticamente a su departamento correspondiente.
     *
     * @param id Identificador único y RUT del empleado.
     * @param apellidos Apellidos del empleado.
     * @param nombre Nombre del empleado.
     * @param correo Correo electrónico corporativo.
     * @param departamento Departamento al cual pertenece dentro de la empresa. */
    public Empleado(String id, String apellidos, String nombre, String correo, Departamento departamento) {
        this.id = id;
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.correo = correo;
        this.departamento = departamento;

        if (this.departamento != null) {
            this.departamento.agregarEmpleado(this);
        }
    }

    /** Simula el envío de una invitación a la reunión al correo del empleado.*/
    @Override
    public void invitar() {
        System.out.println("Invitación enviada al correo del empleado: " + correo);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos + " (ID: " + id + ") - " + departamento.getNombre();
    }
}