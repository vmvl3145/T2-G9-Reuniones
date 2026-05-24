package reuniones.gestion;

import java.time.Instant;

//Representa una nota agregada a una reunion, registra la hora de creacion para ordenarlas en el informe.
public class Nota {
    private String contenido;
    private Instant hora;

    //crea una nueva nota. la hora se registra automaticamente
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
