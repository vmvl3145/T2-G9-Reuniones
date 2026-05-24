package reuniones.gestion;

import java.time.Instant;

//Retraso de un participante en una reunion cuando alguien llega despues de la hora
public class Retraso {
    private Instant hora;

    //crea un retraso con la hora real de llegada del participante.
    public Retraso(Instant hora) {
        this.hora = hora;
    }

    public Instant getHora() { return hora; }
    public void setHora(Instant hora) { this.hora = hora; }

    @Override
    public String toString() {
        return "Retraso registrado a las: " + hora;
    }
}