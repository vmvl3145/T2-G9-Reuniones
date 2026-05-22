package reuniones.gestion;

import reuniones.participantes.Empleado;
import reuniones.participantes.Invitable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.Duration;
import java.time.Instant;
import reuniones.excepciones.EmpleadoNoInvitadoException;
import reuniones.excepciones.ReunionNoIniciadaException;
import reuniones.excepciones.ReunionYaFinalizadaException;

public abstract class Reunion {
    private Date fecha;
    private Instant horaAgendada;
    private Duration duracionAgendada;
    private Instant horaInicio;
    private Instant horaFin;
    private TipoReunion tipoReunion;
    private Empleado organizador;

    private List<Invitacion> invitaciones;
    private List<Asistencia> asistencias;
    private List<Retraso> retrasos;
    private List<Nota> notas;

    public Reunion(Date fecha, Instant horaAgendada, Duration duracionAgendada, TipoReunion tipoReunion, Empleado organizador) {
        this.fecha = fecha;
        this.horaAgendada = horaAgendada;
        this.duracionAgendada = duracionAgendada;
        this.tipoReunion = tipoReunion;
        this.organizador = organizador;

        this.invitaciones =  new ArrayList<>();
        this.asistencias =  new ArrayList<>();
        this.retrasos =  new ArrayList<>();
        this.notas =  new ArrayList<>();
    }

    public void inciar() {
        this.horaInicio = Instant.now();
    }

    public void finalizar() {
        this.horaFin = Instant.now();
    }

    public float calcularTiempoReal() {
        if (horaInicio == null || horaFin == null) {
            return 0f;
        }
        Duration tiempoReal = Duration.between(horaInicio, horaFin);
        return tiempoReal.toMinutes();
    }

    public void invitar(Invitable persona) {
        Invitacion nuevaInvitacion = new Invitacion(persona, Instant.now());
        invitaciones.add(nuevaInvitacion);
    }

    public void registrarAsistencia(Invitable persona, Instant horaLlegada) {
        asistencias.add(new Asistencia(persona));

        if (horaLlegada.isAfter(horaAgendada)) {
            retrasos.add(new Retraso(horaLlegada));
        }
    }

    public void agregarNota(String contenido) {
        notas.add(new Nota(contenido));
    }
    public List<Asistencia> obtenerAsistencias() {
        return asistencias;
    }
    public List<Retraso> obtenerRetrasos() {
        return retrasos;
    }
    public List<Invitable> obtenerAusencias() {
        List<Invitable> ausentes = new ArrayList<>();
        for (Invitacion inv : invitaciones) {
            boolean asistencia = false;
            for (Asistencia a : asistencias) {
                if (a.getParticipante().equals(inv.getInvitado())) {
                    asistencia = true;
                    break;
                }
            }
            if (!asistencia) {
                ausentes.add(inv.getInvitado());
            }
        }
        return ausentes;
    }
    public int obtenerTotalAsistencia() {
        return asistencias.size();
    }

    public float obtenerPorcentajeAsistencia() {
        if (invitaciones.isEmpty()) return 0f;
        return ((float) obtenerTotalAsistencia() / invitaciones.size()) * 100;
    }

    public Date getFecha() {
        return fecha;
    }
    public TipoReunion getTipoReunion() {
        return tipoReunion;
    }
    public Instant getHoraInicio() {
        return horaInicio;
    }
    public Instant getHoraFin() {
        return horaFin;
    }
    public List<Nota> getNotas() { return notas; }
}
