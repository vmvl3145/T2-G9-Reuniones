package gestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reuniones.excepciones.ReunionNoIniciadaException;
import reuniones.excepciones.ReunionYaFinalizadaException;
import reuniones.excepciones.EmpleadoNoInvitadoException;
import reuniones.gestion.Reunion;
import reuniones.gestion.TipoReunion;
import reuniones.participantes.Departamento;
import reuniones.participantes.Empleado;
import reuniones.participantes.InvitadoExterno;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/** Clase de pruebas unitarias encargada de validar la lógica central de las reuniones, abarcando el ciclo de vida, registro de asistencia, retrasos y manejo de excepciones */
public class ReunionTest {

    private Reunion reunion;
    private Empleado organizador;
    private Empleado empleadoInvitado;
    private InvitadoExterno externoInvitado;
    private Empleado noInvitado;

    /** Configuración inicial antes de cada prueba.
     * Instancia a los actores necesarios y una reunión genérica. */
    @BeforeEach
    public void setUp() {
        Departamento dep = new Departamento("IT");
        organizador = new Empleado("101", "Gomez Aguayo","Sofia", "agomez@udec.cl", dep);
        empleadoInvitado = new Empleado("102", "Benitez Estrada", "Juan", "jbenitez@udec.cl", dep);
        externoInvitado = new InvitadoExterno("Esteban", "Muñoz Castro", "emuñoz@udec.cl");
        noInvitado = new Empleado("103", "Boric Font", "Gabriel", "gboric@udec.cl", dep);

        Date fecha = new Date();
        Instant horaAgendada = Instant.now().plus(Duration.ofMinutes(10));
        Duration duracion = Duration.ofMinutes(60);

        reunion = new Reunion(fecha, horaAgendada, duracion, TipoReunion.OTRO, organizador) {};
    }

    // TESTS DE CICLO DE VIDA

    /** Verifica que al iniciar una reunión, la hora de inicio se registre correctamente.
     * @throws ReunionYaFinalizadaException Si la reunión ya había finalizado. */
    @Test
    public void testIniciarReunionExitosa() throws ReunionYaFinalizadaException {
        assertNull(reunion.getHoraInicio(), "La hora de inicio debe ser nula antes de iniciar");
        reunion.iniciar();
        assertNotNull(reunion.getHoraInicio(), "La hora de inicio no debe ser nula tras el inicio de la reunion");
    }

    /** Verifica que se lance una excepción al intentar finalizar una reunión que no ha iniciado. */
    @Test
    public void testFinalizarReunionSinIniciarLanzaExcepcion() {
        assertThrows(ReunionNoIniciadaException.class,
                () -> reunion.finalizar(),
                "Debe lanzar excepcion si se intenta finalizar sin iniciar");
    }

    /** Verifica que se lance una excepción si se intenta iniciar una reunión que ya fue finalizada.
     * @throws Exception Manejo genérico de excepciones de flujo. */
    @Test
    public void testIniciarReunionYaFinalizadaLanzaExcepcion() throws Exception {
        reunion.iniciar();
        reunion.finalizar();

        assertThrows(ReunionYaFinalizadaException.class,
                () -> reunion.iniciar(),
                "Debe lanzar excepcion si se reinicia una reunion finalizada");
    }

    // TESTS DE INVITACIONES Y ASISTENCIAS

    /** Verifica que el método invitar añada correctamente a las personas a la lista de invitaciones.*/
    @Test
    public void testInvitarAgregaALaLista() {
        reunion.invitar(empleadoInvitado);
        reunion.invitar(externoInvitado);

        assertEquals(2, reunion.getInvitaciones().size(), "Debe haber 2 invitaciones");
        assertEquals(empleadoInvitado, reunion.getInvitaciones().get(0).getInvitado());
    }

    /** Verifica que se registre correctamente la asistencia de un invitado puntual.
     * @throws Exception Si la reunión no es válida para la operación. */
    @Test
    public void testRegistrarAsistenciaExitosa() throws Exception {
        reunion.invitar(empleadoInvitado);
        reunion.iniciar();

        reunion.registrarAsistencia(empleadoInvitado, reunion.getHoraAgendada());

        assertEquals(1, reunion.obtenerTotalAsistencia());
        assertEquals(0, reunion.obtenerRetrasos().size(), "No debe haber retrasos si llega a la hora");
    }

    /** Verifica que el registro de un participante que llega tarde genere un registro en la lista de retrasos.
     * @throws Exception Si la reunión no es válida para la operación.  */
    @Test
    public void testRegistrarAsistenciaConRetraso() throws Exception {
        reunion.invitar(externoInvitado);
        reunion.iniciar();

        Instant horaLlegadaTarde = reunion.getHoraAgendada().plus(Duration.ofMinutes(5));
        reunion.registrarAsistencia(externoInvitado, horaLlegadaTarde);

        assertEquals(1, reunion.obtenerTotalAsistencia());
        assertEquals(1, reunion.obtenerRetrasos().size(), "Debe registar un retraso");
    }

    // TEST EXCEPCIONES EN ASISTENCIA

    /** Verifica que no se pueda registrar la asistencia si la reunión no ha marcado su inicio oficial. */
    @Test
    public void testRegistrarAsistenciaAntesDeIniciarLanzaExcepcion() {
        reunion.invitar(empleadoInvitado);
        Instant llegada = Instant.now();

        assertThrows(ReunionNoIniciadaException.class,
                () -> reunion.registrarAsistencia(empleadoInvitado, llegada));
    }

    /** Verifica que un empleado no invitado genere una excepción al intentar marcar asistencia.
     * @throws ReunionYaFinalizadaException Si la reunión ya terminó. */
    @Test
    public void testRegistrarAsistenciaNoInvitadoLanzaExcepcion() throws ReunionYaFinalizadaException {
        reunion.iniciar();
        Instant llegada = Instant.now();

        assertThrows(EmpleadoNoInvitadoException.class,
                () -> reunion.registrarAsistencia(noInvitado, llegada));
    }

    /** Verifica que no se pueda registrar asistencia una vez que el organizador finaliza la reunión.
     * @throws Exception Manejo genérico de excepciones de flujo. */
    @Test
    public void testRegistrarAsistenciaDespuesDeFinalizarLanzaExcepcion() throws Exception {
        reunion.invitar(empleadoInvitado);
        reunion.iniciar();
        reunion.finalizar();

        Instant llegada = Instant.now();
        assertThrows(ReunionYaFinalizadaException.class,
                () -> reunion.registrarAsistencia(empleadoInvitado, llegada));
    }

    // TESTS ESTADISTICAS (Ausencias y Porcentajes

    /** Verifica los cálculos matemáticos y lógicos para el porcentaje de asistencia y conteo de ausentes.
     * @throws Exception Manejo genérico de excepciones de flujo. */
    @Test
    public void testObtenerAusenciasYPorcentaje() throws Exception {
        reunion.invitar(empleadoInvitado);
        reunion.invitar(externoInvitado);

        reunion.iniciar();

        reunion.registrarAsistencia(empleadoInvitado, reunion.getHoraAgendada());

        assertEquals(1, reunion.obtenerAusencias().size(), "Debe haber 1 ausente");
        assertEquals(externoInvitado, reunion.obtenerAusencias().get(0), "El ausente debe ser el externo");

        assertEquals(50.0f, reunion.obtenerPorcentajeAsistencia(), 0.01, "El porcentaje debe ser 50.0%");
    }
}
