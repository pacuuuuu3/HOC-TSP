/**
 * Clase que representa una Ciudad del TSP
 * @author Victor Zamora Gutierrez
 * @version 1.0 
 */
public class Ciudad{
    public final String nombre; /* Nombre de la ciudad */
    public final double latitud; /* Coordenada de latitud */
    public final double longitud; /* Coordenada de longitud */
    public final int id; /* ID de la ciudad en la base de datos */
    
    /**
     * Constructor 
     * @param nombre - El nombre de la ciudad
     * @param latitud - Coordenada de latitud
     * @param longitud - Coordenada de longitud
     * @param id - El id de la ciudad en la base de datos 
     */
    public Ciudad(String nombre, double latitud, double longitud, int id){
	this.latitud = latitud;
	this.longitud = longitud;
	this.id = id;
    }
}
