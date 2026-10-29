package modelo;

public class Arista  {

    private Vertice origen;
    private Vertice destino;

    
    public Arista(Vertice origen, Vertice destino) {
        this.origen = origen;
        this.destino = destino;
        
    }
    
    public Vertice getOrigen() {
        return origen;
    }
    
    public Vertice getDestino() {
        return destino;
    }
    
   
    
    public Vertice obtenerOpuesto(Vertice v) {
	    	if (getOrigen().equals(v)) return getDestino();
	    	if (getDestino().equals(v)) return getOrigen();
	    	throw new IllegalArgumentException("El vertice no pertenece a esta arista.");
    }    
    
    @Override
    public String toString() {
        return origen + " -- " + destino ;
    }  
}
