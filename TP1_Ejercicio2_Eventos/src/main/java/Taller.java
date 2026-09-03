public class Taller extends Actividad{
    private boolean requiereNotebook;
    public Taller(int id, String titulo, int cupo,boolean requiereNotebook){
        super(id,titulo,cupo);
        this.requiereNotebook = requiereNotebook;    
    }
    public boolean getRequiereNotebook(){
        return requiereNotebook;
    }
    @Override public String getTipo() {
        return this.getClass().getSimpleName();
    }
    @Override public double calcularCostoMateriales() {
        if (requiereNotebook) return 5000;
        else 
            return 2000;
    }
}
