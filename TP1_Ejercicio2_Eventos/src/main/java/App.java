import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal desde la cual se crean y vinculan los objetos del modelo.
 * Permite ejercitar dependencia o uso desde App hacia las clases del dominio.
 */
public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        int id = 1;

        /* Se crean estudiantes */
        List<Estudiante> estudiantes = new ArrayList<>();

        System.out.println("REGISTRO DE ESTUDIANTES: ");
        System.out.println("======================");

        while (continuar) {
            System.out.println("Ingese legajo del estudiante: ");
            String legajo = scanner.nextLine();
            System.out.println("Ingese nombre y apellido del estudiante: ");
            String apenomb = scanner.nextLine();
            estudiantes.add(new Estudiante(legajo, apenomb));
            System.out.println("desea crear otro estudiante  S/N?");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            continuar = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí"));
        }

        /* Se itera construyendo eventos */
        System.out.println("\n\nREGISTRO DE EVENTOS: ");
        System.out.println("====================");
        continuar = true;
        while (continuar) {
            /* Se requieren datos por consola para construir un evento */
            System.out.println("Ingese un titulo para el evento: ");
            String titulo = scanner.nextLine();
            System.out.println("Ingese el costo base:  ");
            double costoBase = scanner.nextDouble();
            scanner.nextLine(); // limpia el Enter pendiente
            System.out.println("El evento tendra costo para los participantes S/N?");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            boolean esGratuito = true;
            if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                esGratuito = false;
            }

            /* Se construye un objeto del tipo EventoUniversitario */
            EventoUniversitario evento = new EventoUniversitario(
                    "EVT-" + id,
                    titulo,
                    costoBase,
                    esGratuito
            );
            /* Se crea una sala y se asigna al evento */
            System.out.println("Ingese el nombre de la sala donde se realizará el evento: ");
            String nombreSala = scanner.nextLine();
            Sala sala = new Sala(id, nombreSala);
            evento.asignarSala(sala);

            /* Se crean las actividades del evento */
            System.out.println("\n\nREGISTRO DE ACTIVIDADES PARA EL EVENTO " + evento.getTitulo());
            System.out.println("================================================================");
            int idActividad = 1;
            boolean continuarActividades = true;
            while (continuarActividades) {
                System.out.println("Ingese el título de la actividad: ");
                String tituloActividad = scanner.nextLine();
                System.out.println("Ingese el cupo máximo de estudiantes admitidos para la actividad: ");
                int cupo = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer
                System.out.println("Ingese el tipo de actividad (taller/charla): ");
                String tipoActividad = scanner.nextLine().trim().toLowerCase();
                String actividadDisertante = "";
                boolean actividadRequiereNotebook = false; 

                if (tipoActividad.equals("charla")) {
                    System.out.println("Ingese el disertante de la charla: ");
                    actividadDisertante = scanner.nextLine();
                } else if (tipoActividad.equals("taller")) {
                    System.out.println("Ingese si vas a necesitar una notebook para la actividad (S/N): ");
                    String notebookPregunta = scanner.nextLine().trim().toLowerCase();
                    actividadRequiereNotebook = (notebookPregunta.equals("s") || notebookPregunta.equals("si") || notebookPregunta.equals("sí"));
                }
                
                evento.crearActividad(idActividad, tituloActividad, cupo, tipoActividad, actividadDisertante, actividadRequiereNotebook);
                System.out.println("Desea crear otra actividad para el evento " + evento.getTitulo() + " S/N?");
                respuesta = scanner.nextLine().trim().toLowerCase();
                continuarActividades = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí"));
                ++idActividad;
            }

            /* Se inscriben estudiantes en actividades */
            System.out.println("\n\nINSCRIPCION DE ESTUDIANTES EN ACTIVIDADES DEL EVENTO " + evento.getTitulo());
            System.out.println("===============================================================================");
            boolean continuarInscripcion = true;
            while (continuarInscripcion) {
                System.out.println("Ingese legajo del estudiante a inscribir: ");
                String legajoInscripcion = scanner.nextLine();
                System.out.println("Ingese id de la Actividad: ");
                int idIngresado = scanner.nextInt();
                scanner.nextLine(); // se consume linea

                int indexActividad = idIngresado - 1;
                if (indexActividad >= 0 && indexActividad < evento.getActividades().size()) {
                    for (Estudiante estudiante : estudiantes) {
                        if (estudiante.getLegajo().equals(legajoInscripcion)) {
                            evento.getActividades().get(indexActividad).inscribir(estudiante);
                            System.out.println("Estudiante inscrito con éxito.");
                        }
                    }
                } else {
                    System.out.println("Error: El ID de actividad ingresado no existe.");
                }

                System.out.println("Desea generar otra inscripción S/N?");
                respuesta = scanner.nextLine().trim().toLowerCase();
                continuarInscripcion = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí"));
            }

            /* Se muestran datos del evento */
            System.out.println("\n\n DATOS DEL EVENTO");
            evento.mostrarDatos();

            /* Se consulta si se desea continuar creando eventos */
            System.out.println("\n\nDesea crear otro evento S/N?");
            respuesta = scanner.nextLine().trim().toLowerCase();
            continuar = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí"));
            id++;
        }

        /* Se muestra la cantidad total de eventos creados */
        System.out.println("\n\nTOTAL DE EVENTOS CREADOS: " + EventoUniversitario.getCantidadEventos());
    }
}