package reuniones.gestion;

import java.time.Instant;

/** Representa una nota agregada a una reuniön.
 * Registra automaticamente la hora de creacion para ordenarlas cronológicamente en el informe. */
public class Nota {
    private String contenido;
    private Instant hora;

    /**Crea una nueva nota. La hora se registra al instante actual del sistema.
     * @param contenido El texto o apunte de la nota. */
    public Nota(String contenido) {
        this.contenido = contenido;
        this.hora = Instant.now();
    }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public Instant getHora() { return hora; }

    @Override
    public String toString() {
        return "[" + hora + "] " + contenido;
    }
}
