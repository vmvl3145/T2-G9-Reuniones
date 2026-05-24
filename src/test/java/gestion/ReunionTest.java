package gestion;

public class ReunionTest {

    private Reunion reunion;
    private Empleado organizador;
    private Empleado empleadoInvitado;
    private InvitadoExterno externoInvitado;
    private Empleado noInvitado;

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

    @Test
    public void testIniciarReunionExitosa() throws ReunionYaFinalizadaException {
        assertNull(reunion.getHoraInicio(), "La hora de inicio debe ser nula antes de iniciar");
        reunion.iniciar();
        assertNotNull(reunion.getHoraInicio(), "La hora de inicio no debe ser nula tras el inicio de la reunion");
    }

    @Test
    public void testFinalizarReunionSinIniciarLanzaExcepcion() {
        assertThrows(ReunionNoIniciadaException.class,
                () -> reunion.finalizar(),
                "Debe lanzar excepcion si se intenta finalizar sin iniciar");
    }

    @Test
    public void testIniciarReunionYaFinalizadaLanzaExcepcion() throws Exception {
        reunion.iniciar();
        reunion.finalizar();

        assertThrows(ReunionYaFinalizadaException.class,
                () -> reunion.iniciar(),
                "Debe lanzar excepcion si se reinicia una reunion finalizada");
    }

    // TESTS DE INVITACIONES Y ASISTENCIAS

    @Test
    public void testInvitarAgregaALaLista() {
        reunion.invitar(empleadoInvitado);
        reunion.invitar(externoInvitado);

        assertEquals(2, reunion.getInvitaciones().size(), "Debe haber 2 invitaciones");
        assertEquals(empleadoInvitado, reunion.getInvitaciones().get(0).getInvitado());
    }

    @Test
    public void testRegistrarAsistenciaExitosa() throws Exception {
        reunion.invitar(empleadoInvitado);
        reunion.iniciar();

        reunion.registrarAsistencia(empleadoInvitado, reunion.getHoraAgendada());

        assertEquals(1, reunion.obtenerTotalAsistencia());
        assertEquals(0, reunion.obtenerRetrasos().size(), "No debe haber retrasos si llega a la hora");
    }

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

    @Test
    public void testRegistrarAsistenciaAntesDeIniciarLanzaExcepcion() {
        reunion.invitar(empleadoInvitado);
        Instant llegada = Instant.now();

        assertThrows(ReunionNoIniciadaException.class,
                () -> reunion.registrarAsistencia(empleadoInvitado, llegada));
    }

    @Test
    public void testRegistrarAsistenciaNoInvitadoLanzaExcepcion() throws ReunionYaFinalizadaException {
        reunion.iniciar();
        Instant llegada = Instant.now();

        assertThrows(EmpleadoNoInvitadoException.class,
                () -> reunion.registrarAsistencia(noInvitado, llegada));
    }

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
