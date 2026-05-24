# T2-G9-Reuniones
Sistema de Gestión de Reuniones - Tarea 2 - Grupo 9

## Integrantes del Grupo
* Joaquín Adauy - 2025405466
* Joaquín Navarrete - 202542012
* Vicente Vergara - 2025431734

## Diagrama UML
A continuación se presenta el modelo actualizado del sistema:

[Haz clic aquí para ver el Diagrama UML Final (formato PDF)](diagrama_uml.pdf)

## Justificación de Modificaciones en el UML

Para cumplir con los nuevos requerimientos del sistema, realizamos los siguientes cambios al modelo original:

### Inclusión de Invitados Externos
El modelo inicial solo contemplaba a la clase "Empleado" como asistente a las reuniones. En el diseño final integramos la clase "InvitadoExterno" y detallando de mayor manera la interfaz "Invitable". Esto permite que la clase "Reunion" maneje las colecciones de "Invitacion" y "Asistencia" de forma polimórfica, tratando a personal interno como externo por igual

### Separación de Responsabilidades con InformeReunion
En el diagrama original no se contemplaba el requerimiento de la generación de reportes. En lugar de sobrecargar la clase de Reunion con métodos de entrada/salido (Input/Output) para escribir archivoooos .txt, se decidió aislar esta creando la clase dedicada "InformeReunion". De esta manera, el modelo respeta la responsabilidad única, "Reunion" administra toda la lógica de negocio y el ciclo de vida, mientras que "InformeReunion" consume estos datos para el formateo.

### Excepciones Personalizadas
El diseño original carecia de un sistema para manejo de errores de flujo. Para solucionar esto mismo, incorporamos un paquete con una jerarquía de excepciones propias (ReunionNoIniciadaException, ReunionYaFinalizadaException, EmpleadoNoInvitadoException) que extienden desde la clase "Exception". Esto proteje la integridad lógica de la aplicación, bloqueando algún tipo de intento de registrar ausencias en reuniones no iniciadas o intentar invitar a personas no autorizadas.

### Correción de Relaciones de Dependencia y Composición
En el diagrama original, clases como "Retraso" o "Nota" presentaban relaciones ambiguas o dependencias directas incorrectas (Como lo era vincular un retraso de manera directa a una asistencia). En el diagrama final se corrigió, ahora "Reunion" siendo la administradora central y dueña de todas las listas independientes de asistencias, retrasos, notas e invitaciones.

### Automatización de Invitaciones Masivas
Se añadio el método "invitarDepartamento(Departamento dpto)" dentro de la clase abstracta de "Reunion". Esto para aprovechar de mejor manera la cardinalidad que existe entre "Departamento" y "Empleado" para iterar de manera interna a los miembros de una unidad y realizar la creación masiva de invitaciones.
