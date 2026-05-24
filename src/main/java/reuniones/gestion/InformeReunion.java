package reuniones.gestion;

import reuniones.excepciones.ReunionNoIniciadaException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/** Genera y guarda el informe de una reunion en un archivo .txt.
 * Contiene los detalles de tiempos, participantes, ausencias, retrasos y notas. */
public class InformeReunion {
    private Reunion reunion;

    /**Crea un informe para la reunion especificada.
     * @param reunion La reunion de la cual se generara el informe. */
    public InformeReunion(Reunion reunion) {
        this.reunion = reunion;
    }

    /** Genera el contenido del informe recopilando los datos de la reunion.
     * @return Un String con el formato completo del informe.
     * @throws ReunionNoIniciadaException Si se intenta generar un informe de una reunion incompleta. */
    public String generarContenido() throws ReunionNoIniciadaException {
        if (reunion.getHoraInicio() == null || reunion.getHoraFin() == null) {
            throw new ReunionNoIniciadaException("El informe solo puede generarse de una reunion finalizada.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("           INFORME DE REUNION\n");
        sb.append("========================================\n\n");

        sb.append("Fecha: ").append(reunion.getFecha()).append("\n");
        sb.append("Hora agendada: ").append(reunion.getHoraAgendada()).append("\n");
        sb.append("Hora de inicio: ").append(reunion.getHoraInicio()).append("\n");
        sb.append("Hora de fin: ").append(reunion.getHoraFin()).append("\n");
        sb.append("Duracion real: ").append((long) reunion.calcularTiempoReal()).append(" minuto(s)\n");
        sb.append("Duracion prevista: ").append(reunion.getDuracionAgendada().toMinutes()).append(" minuto(s)\n\n");
        sb.append("Tipo de reunion: ").append(reunion.getTipoReunion()).append("\n");
        sb.append("Organizador: ").append(reunion.getOrganizador()).append("\n");

        if (reunion instanceof ReunionVirtual) {
            sb.append("Modalidad: Virtual\n");
            sb.append("Enlace: ").append(((ReunionVirtual) reunion).getEnlace()).append("\n");
        } else if (reunion instanceof ReunionPresencial) {
            sb.append("Modalidad: Presencial\n");
            sb.append("Sala: ").append(((ReunionPresencial) reunion).getSala()).append("\n");
        }
        sb.append("\n--- PARTICIPANTES (").append(reunion.obtenerTotalAsistencia()).append(") ---\n");
        for (Asistencia a : reunion.obtenerAsistencias()) {
            sb.append("  + ").append(a.getParticipante().getNombre())
              .append(" ").append(a.getParticipante().getApellidos()).append("\n");
        }

        sb.append("\n--- AUSENTES (").append(reunion.obtenerAusencias().size()).append(") ---\n");
        reunion.obtenerAusencias().forEach(p ->
            sb.append("  - ").append(p.getNombre()).append(" ").append(p.getApellidos()).append("\n")
        );

        sb.append("\n--- RETRASOS (").append(reunion.obtenerRetrasos().size()).append(") ---\n");
        for (Retraso r : reunion.obtenerRetrasos()) {
            sb.append("  ").append(r).append("\n");
        }
        sb.append(String.format("\nPorcentaje de asistencia: %.1f%%\n", reunion.obtenerPorcentajeAsistencia()));

        sb.append("\n--- NOTAS ---\n");
        List<Nota> notasOrdenadas = reunion.getNotas().stream()
                .sorted((a, b) -> a.getHora().compareTo(b.getHora()))
                .toList();
        for (Nota n : notasOrdenadas) {
            sb.append("  ").append(n).append("\n");
        }
        sb.append("\n========================================\n");
        return sb.toString();
    }

    /** Guarda el informe generado en un archivo fisico de texto.
     * @param rutaArchivo La ruta y nombre del archivo (ej. "informe.txt").
     * @throws ReunionNoIniciadaException Si la reunion no es valida para el informe.
     * @throws IOException Si ocurre un error al escribir el archivo en el disco. */
    public void guardarEnArchivo(String rutaArchivo) throws ReunionNoIniciadaException, IOException {
        String contenido = generarContenido();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(contenido);
        }
        System.out.println("archivo guardado en: " + rutaArchivo);
    }

    @Override
    public String toString() {
        try {
            return generarContenido();
        } catch (ReunionNoIniciadaException e) {
            return "Informe no disponible: " + e.getMessage();
        }
    }
}