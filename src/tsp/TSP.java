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
    public static final double DEFAULT_DISTANCE = setDefaultDistance(); /* Distancia por omisión de dos ciudades desconectadas */
    
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
	}finally{
	    try { if (cuenta != null) cuenta.close(); } catch (Exception e) {};
	}
	return tamano;
    }

    /**
     * Regresa la distancia entre dos ciudades.
     * Si esta no existe, utiliza un valor por omisión. 
     * @param c1 - Id de la primera ciudad
     * @param c2 - Id de la segunda ciudad
     * @return La distancia entre las dos ciudades
     */
    public static double getDistancia(int c1, int c2){
	if(distancias[c1][c2] > 0)
	    return distancias[c1][c2];
	else if(distancias[c2][c1] > 0)
	    return distancias[c2][c1];
	else
	    return DEFAULT_DISTANCE;
    }

    /**
     * Regresa el valor de la distancia por omisión.
     * Para este método, utilizo la máxima distancia * 2.
     * @return La distancia por omisión entre dos ciudades desconectadas.     
     */
    public static double setDefaultDistance(){
	double max = 0; /* La máxima distancia */
	ResultSet rs = null;
	try{
	    rs = c.consulta("SELECT MAX(distance) as maxDistance from connections"); /* Dejo que SQL saque la máxima distancia */
	    max = rs.getDouble("maxDistance"); /* Saco la máxima distancia */
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}finally{
	    try { if (rs != null) rs.close(); } catch (Exception e) {};
	}
	return max*2;
    }
    
    /**
     * Llena el arreglo de ciudades utilizando la base de datos 
     */
    public static void llenaCiudades(){
	ResultSet rs = null;
	try{
	    int tamano = getTamano(); /* Tamaño del arreglo de Ciudades */
	    ciudades = new Ciudad[tamano+1];
	    rs = c.consulta("SELECT * FROM cities"); /* Conjunto de ciudades */
	    while(rs.next()){
		Ciudad nueva = new Ciudad(rs.getString("country"), rs.getString("name"), rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getInt("id"), rs.getInt("population"));
		ciudades[nueva.getId()] = nueva;
	    }
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}finally{
	    try { if (rs != null) rs.close(); } catch (Exception e) {};
	}
    }

    /**
     * Llena el arreglo de distancias
     */
    public static void llenaDistancias(){
	ResultSet rs = null;
	try{
	    int tamano = ciudades.length; /* Voy a suponer que el arreglo de Ciudades ya está inicializado */
	    distancias = new double[tamano][tamano]; /* Inicializo el arreglo de distancias con tamaño (#Ciudades+1)^2, 
						   * aunque podría ser menos si optimizara más */
	    rs = c.consulta("SELECT * FROM connections"); /* Tabla de conexiones */
	    while(rs.next()){
		int indice1, indice2; /* Índices de las ciudades a conectar */
		indice1 = rs.getInt("id_city_1");
		indice2 = rs.getInt("id_city_2");
		distancias[indice1][indice2] = rs.getDouble("distance");
	    }
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}finally{
	    try { if (rs != null) rs.close(); } catch (Exception e) {};
	}
    }

    /** 
     * Inicializa por completo una instancia de TSP 
     */
    public static void inicializa(){
	if(c == null)
	    c = new Conexion();
	llenaCiudades();
	llenaDistancias();
	c.cierraConexion();
    }

    /**
     * Algoritmo para calcular un lote
     * @param s - La solución para la cuál calculamos el lote
     */
    
    
}
