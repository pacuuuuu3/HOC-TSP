package tsp;

/**
 * Programa que utiliza recocido simulado (Simulated Annealing) para resolver una versión modificada del TSP
 * @author Victor Zamora Gutierrez
 * @version 1.0
 */

import tsp.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TSP{

    private Ciudad[] ciudades; /* Arreglo de ciudades */

    /** 
     * Regresa el tamano del arreglo de ciudades 
     * @return el tamano del arreglo de ciudades 
     */
    public static int getTamano(){
	int tamano = -1; /* Tamaño de la tabla */
	Conexion c = new Conexion(); /* Nos conectamos a la base */
	ResultSet cuenta = c.consulta("SELECT count(*) FROM cities"); /* Contamos las ciudades */
       	try{
	    tamano = cuenta.getInt(1); /* El tamaño de la tabla */
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}
	return tamano;
    }
    
    /**
     * Llena el arreglo de ciudades utilizando la base de datos 
     */
    public void llenaCiudades(){
	return;
    }	

}
