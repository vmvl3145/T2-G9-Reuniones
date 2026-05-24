package gestion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import reuniones.excepciones.ReunionNoIniciadaException;
import reuniones.gestion.InformeReunion;
import reuniones.gestion.TipoReunion;
import reuniones.participantes.Departamento;
import reuniones.participantes.Empleado;

import reuniones.gestion.Reunion;
import reuniones.gestion.ReunionVirtual;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class InformeReunionTest {

    // Metodo auxiliar para crear datos de prueba
    private ReunionVirtual crearReunionDePrueba() {
        Date fecha = new Date();
        Instant horaAgendada = Instant.now();
        Duration duracion = Duration.ofMinutes(60);
        Departamento d = new Departamento("Logistica");
        Empleado organizador = new Empleado("23882513-9", "Gonzalez Gutierrez", "Pablo", "pgonzalez2014@udec.cl", d);

        return new ReunionVirtual(fecha, horaAgendada, duracion, TipoReunion.TECNICA, organizador, "https://zoom.cl/testReunion");
    }

    @Test
    public void testGenerarContenidoLanzaExcepcionSiNoEstaFinalizada() {
        Reunion reuInc = crearReunionDePrueba();

        InformeReunion inf = new InformeReunion(reuInc);

        ReunionNoIniciadaException excepcion = assertThrows(
                ReunionNoIniciadaException.class,
                () -> inf.generarContenido(),
                "Deberia lanzar excepcion si la reunion no tiene hora de inicio/fin"
        );

        assertEquals("El informe solo puede generarse de una reunion finalizada.", excepcion.getMessage());
    }

    @Test
    public void testGenerarContenidoReunionVirtualExitosa() throws Exception {
        Reunion reuLista = crearReunionDePrueba();

        reuLista.iniciar();
        Thread.sleep(1000);
        reuLista.finalizar();

        reuLista.agregarNota("Se acordo usar el lenguaje Java para el proyecto asociado al expendedor");

        InformeReunion inf = new InformeReunion(reuLista);

        String res = inf.generarContenido();

        assertNotNull(res, "El contenido del informe no deberia de ser null");
        assertTrue(res.contains("INFORME DE REUNION"), "Debe contener el titulo");
        assertTrue(res.contains("Modalidad: Virtual"), "Debe contener la modalidad de la reunion");
        assertTrue(res.contains("Se acordo usar el lenguaje Java para el proyecto asociado al expendedor"),
                "Debe contener la nota creada al finalizar la reunion");
    }

    @Test
    public void testGuardarEnArchivoFisico(@TempDir Path carpetaTemporal) throws Exception {
        Reunion reuValida = crearReunionDePrueba();
        reuValida.iniciar();
        Thread.sleep(100);
        reuValida.finalizar();

        InformeReunion inf = new InformeReunion(reuValida);

        Path rutaArchivo = carpetaTemporal.resolve("informeTest.txt");

        inf.guardarEnArchivo(rutaArchivo.toString());

        assertTrue(Files.exists(rutaArchivo), "El archivo deberia haberse creado en el disco");

        String contenidoGuardado = Files.readString(rutaArchivo);
        assertTrue(contenidoGuardado.contains("INFORME DE REUNION"));
    }
}
