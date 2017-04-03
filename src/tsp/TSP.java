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

    public static Ciudad[] ciudades; /* Arreglo de ciudades */
    public static double[][] distancias; /* Arreglo de distancias entre las ciudades */
    private static Conexion c = new Conexion(); /* Conexión a la base de datos */
    
    /** 
     * Regresa el tamano del arreglo de ciudades 
     * @return el tamano del arreglo de ciudades 
     */
    public static int getTamano(){
	int tamano = -1; /* Tamaño de la tabla */
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
    public static void llenaCiudades(){
	try{
	    int tamano = getTamano(); /* Tamaño del arreglo de Ciudades */
	    ciudades = new Ciudad[tamano+1];
	    ResultSet rs = c.consulta("SELECT * FROM cities"); /* Conjunto de ciudades */
	    while(rs.next()){
		Ciudad nueva = new Ciudad(rs.getString("country"), rs.getString("name"), rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getInt("id"), rs.getInt("population"));
		ciudades[nueva.getId()] = nueva;
	    }
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}
    }

    /**
     * Llena el arreglo de distancias
     */
    public static void llenaDistancias(){
	try{
	    int tamano = ciudades.length; /* Voy a suponer que el arreglo de Ciudades ya está inicializado */
	    distancias = new double[tamano][tamano]; /* Inicializo el arreglo de distancias con tamaño (#Ciudades+1)^2, 
						   * aunque podría ser menos si optimizara más */
	    ResultSet rs = c.consulta("SELECT * FROM connections"); /* Tabla de conexiones */
	    while(rs.next()){
		int indice1, indice2; /* Índices de las ciudades a conectar */
		indice1 = rs.getInt("id_city_1");
		indice2 = rs.getInt("id_city_2");
		distancias[indice1][indice2] = rs.getDouble("distance");
	    }
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}
    }
}
