package tsp;

/**
 * Clase que representa una Ciudad del TSP
 * @author Victor Zamora Gutierrez
 * @version 1.0 
 */
public class Ciudad{
    public final String pais; /* País al que pertenece la ciudad */
    public final String nombre; /* Nombre de la ciudad */
    public final double latitud; /* Coordenada de latitud */
    public final double longitud; /* Coordenada de longitud */
    public final int id; /* ID de la ciudad en la base de datos */
    public final int poblacion; /* La población de la ciudad */
    
    /**
     * Constructor
     * @param pais - País al que pertenece la ciudad
     * @param nombre - El nombre de la ciudad
     * @param latitud - Coordenada de latitud
     * @param longitud - Coordenada de longitud
     * @param id - El id de la ciudad en la base de datos
     * @param poblacion - La poblacion de la ciudad
     */
    public Ciudad(String pais, String nombre, double latitud, double longitud, int id, int poblacion){
	this.pais = pais;
	this.nombre = nombre;
	this.latitud = latitud;
	this.longitud = longitud;
	this.id = id;
	this.poblacion = poblacion;
    }

    /**
     * Regresa el id de la ciudad en la tabla
     * @return El id de la ciudad 
     */
    public int getId(){
	return this.id;
    }

    /**
     * Regresa la Ciudad como una cadena 
     * @return Cadena que representa a la ciudad 
     */
    @Override public String toString(){
	return this.nombre + ", " + this.pais;
    }
}
