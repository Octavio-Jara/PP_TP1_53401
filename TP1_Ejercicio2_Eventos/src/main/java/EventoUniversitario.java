    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;
    /**
     * EventoUniversitario compone una o más actividades.
     * También agrega una sala, que puede existir independientemente del evento.
     */
    public class EventoUniversitario {
        private final String Id;
        private String titulo;
        private double costoBase;
        private boolean gratuito;

        /* Clases relacionadas  */
        private Sala sala;
        private List <Actividad> actividades;

        /* Variables de clase */
        private static int cantidadEventos;

        /* Inicializador estático */
        static {
            cantidadEventos = 0;
            System.out.println("Inicializador estático: se cargó la clase EventoUniversitario.");
        }

        public EventoUniversitario(String id, String nombre,  double costo, boolean esGratuito) {
            this.Id = id;
            setTitulo(nombre); //se usa setTitulo en lugar de asignación directa porque hay validación de que no sea nulo.
            this.gratuito = esGratuito;
            this.costoBase = gratuito ? 0 : costo;        cantidadEventos++;

            /* Aquí notar como se implementa la relación de composición como una relación estructural del tipo Todo-Parte fuerte .
            * Si se destruye el evento se destruirán también sus actividades.
            * Es decir, la vida útil de cada actividad está fuertemente ligada a la vida útil del evento. */
            this.actividades = new ArrayList<>();
        }

        public EventoUniversitario(EventoUniversitario otroEvento) {
            this(
                    otroEvento.Id + "-COPIA",
                    otroEvento.titulo,
                    otroEvento.costoBase,
                    otroEvento.gratuito
            );
        }

        public String getId() {
            return Id;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String nombre) {
            if (nombre != null && !nombre.isBlank())
                this.titulo = nombre;
        }
    
        public double calcularCostoEstimado() {
            if (this.gratuito){
                return 0;
            }
            double sumarCostoActividades = 0;
            for (Actividad actividad : actividades){
                sumarCostoActividades += actividad.calcularCostoMateriales();
            }
            return (this.costoBase + sumarCostoActividades) * 1.21; // 21% de impuestos
        }

        public Sala getSala() {
            return sala;
        }

        /* Implementa la agregación dinámica. Un evento se realiza en una sala, pero la relación Todo-Parte es débil.
        * Si el evento no se realiza y el objeto que lo representa se destruye, la sala sigue existiendo y puede asignarse a otro evento. */
        public void asignarSala(Sala sala) {
                this.sala = sala;
        }

        /**
         * Representa la composición: la actividad se crea para el evento y queda contenida por él.
         * La relación Todo-Parte es fuerte: si el evento se destruye, las actividades también se destruyen.
         */
        public void crearActividad(int id, String titulo, int cupo, String tipo,String disertante,boolean requiereNotebook) {
            if (tipo.trim().toLowerCase().equals("charla")){
                Actividad actividad = new Charla(id,titulo,cupo,disertante);
                this.actividades.add(actividad);
            }else if (tipo.trim().toLowerCase().equals("taller")){
                Actividad actividad = new Taller(id,titulo,cupo,requiereNotebook);
                this.actividades.add(actividad);
            }
        }

        public List<Actividad>  getActividades() {
            /* Se retorna una lista inmodificable para que mantener el encapsulamiento logrado con la composición
            * y que no puedan agregar actividades desde afuera. */
            return Collections.unmodifiableList(actividades);
        }

        public void  mostrarDatos() {
            System.out.println("===================================================================================");
            System.out.println("Evento codigo=" + Id);
            System.out.println("TÍtulo=" + titulo);
            System.out.println("Costo=" + this.calcularCostoEstimado());
            System.out.println("Sala asignada: " + (sala != null ? sala.getNombre() : "Sin sala")+"\n");
            System.out.println("Actividades:");
            System.out.println("____________");
            for (Actividad actividad : actividades) {
                System.out.println("- " + actividad.getTitulo() + " (id=" + actividad.getId() + ")" + " - Cupo máximo: " + actividad.getCupoMaximo());
                actividad.mostrarInscripciones();
            }
            System.out.println("=====================================================================================");
        }

        public static int getCantidadEventos() {
            return cantidadEventos;
        }
    }
