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

/** Clase de pruebas unitarias para validar la correcta generación y guardado físico de los informes de reunión.*/
public class InformeReunionTest {

    /**Método auxiliar para crear una reunión virtual con datos de prueba predefinidos.
     *
     * @return Una instancia de ReunionVirtual lista para ser utilizada en los tests.*/
    private ReunionVirtual crearReunionDePrueba() {
        Date fecha = new Date();
        Instant horaAgendada = Instant.now();
        Duration duracion = Duration.ofMinutes(60);
        Departamento d = new Departamento("Logistica");
        Empleado organizador = new Empleado("23882513-9", "Gonzalez Gutierrez", "Pablo", "pgonzalez2014@udec.cl", d);

        return new ReunionVirtual(fecha, horaAgendada, duracion, TipoReunion.TECNICA, organizador, "https://zoom.cl/testReunion");
    }

    /** Verifica que el sistema lance una excepción si se intenta generar el contenidode un informe para una reunión que no ha sido finalizada. */
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

    /** Verifica que el contenido del informe se genere correctamente en formato String, comprobando que incluya las secciones clave y las notas agregadas.
     * @throws Exception Si ocurre un error al pausar el hilo o generar el contenido.*/
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

    /**
     * Verifica que el informe se pueda escribir correctamente en un archivo de texto físico (.txt) utilizando un directorio temporal de JUnit.
     * @param carpetaTemporal Directorio inyectado por JUnit para pruebas de I/O.
     * @throws Exception Si ocurre un error durante la escritura del archivo físico. */
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
