package reuniones.participantes;

import java.util.ArrayList;
import java.util.List;

/** Representa un departamento o área estructural dentro de la empresa.
 * Agrupa y administra a los empleados que pertenecen a una misma área.*/
public class Departamento {
    private String nombre;
    private List<Empleado> empleados;

    /** Crea un nuevo departamento con el nombre especificado.
     * Inicializa automáticamente la lista de trabajadores vacía.
     * @param nombre El nombre del área o departamento. */
    public Departamento(String nombre) {
        this.nombre = nombre;
        this.empleados = new ArrayList<>();
    }

    /** Añade un nuevo empleado a la lista interna de este departamento.
     * Este método se llama automáticamente al instanciar un Empleado.
     * @param empleado El objeto Empleado que será vinculado. */
    public void agregarEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
    }

    /** Calcula la cantidad total de trabajadores vinculados al departamento.
     * @return El número exacto de empleados en la lista. */
    public int obtenerCantidadEmpleados() {
        return this.empleados.size();
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<Empleado> getEmpleados() {
        return empleados;
    }
    @Override
    public String toString() {
        return "Departamento: " + nombre + " (" + obtenerCantidadEmpleados() + " empleados)";
    }
}