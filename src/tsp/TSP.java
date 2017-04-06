package tsp;

/**
 * Programa que utiliza recocido simulado (Simulated Annealing) para resolver una versión modificada del TSP
 * @author Victor Zamora Gutierrez
 * @version 1.0
 */

import utileria.Par;
import tsp.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class TSP{

    public static Ciudad[] ciudades; /* Arreglo de ciudades */
    public static double[][] distancias; /* Arreglo de distancias entre las ciudades */
    private static Conexion c; /* Conexión a la base de datos */
    public static final double DEFAULT_DISTANCE = setDefaultDistance(); /* Distancia por omisión de dos ciudades desconectadas */
    public static final int L = 500; /* Tamaño del lote */
    public static Random random; /* Generador de números aleatorios */
    
    
    /**
     * Singleton para obtener la conexión.
     * @return Una conexión a la base 
     */
    public static Conexion getConexion(){
	if(c == null || !c.valida())
	    return new Conexion();
	return c;
    }
    
    /** 
     * Regresa el tamano del arreglo de ciudades 
     * @return el tamano del arreglo de ciudades 
     */
    public static int getTamano(){
	c = getConexion();
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
	else
	    return DEFAULT_DISTANCE;
    }

    /**
     * Regresa el valor de la distancia por omisión.
     * Para este método, utilizo la máxima distancia * 2.
     * @return La distancia por omisión entre dos ciudades desconectadas.     
     */
    public static double setDefaultDistance(){
	c = getConexion();
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
	c.cierraConexion();
	return max*2;
    }
    
    /**
     * Llena el arreglo de ciudades utilizando la base de datos 
     */
    public static void llenaCiudades(){
	c = getConexion();
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
	c = getConexion();
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
		distancias[indice2][indice1] = distancias[indice1][indice2];
	    }
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}finally{
	    try { if (rs != null) rs.close(); } catch (Exception e) {};
	}
    }

    /** 
     * Inicializa por completo una instancia de TSP
     * @param seed - La semilla del generador de números aleatorios.
     */
    public static void inicializa(long seed){
	random = new Random(seed);
	llenaCiudades();
	llenaDistancias();
	c.cierraConexion();
    }

    /**
     * Algoritmo para calcular un lote
     * @param s - La solución para la cuál calculamos el lote
     */
    public Par<Double, Solucion> calculaLote(double temperatura, Solucion s){
	int c = 0; /* Número de soluciones aceptadas hasta el momento */
	double r = 0; /* La suma de los costos de las soluciones */
	Solucion s1 = null; /* La siguiente solución por calcular */
	while(c < L){
	    s1 = s.vecino();
	    if(s1.costo() <= (s.costo() + temperatura)){
		s = s1;
		c++;
		r = s1.costo();
	    }
	}
	return new Par<>(r/L, s); /* Promedio de las soluciones aceptadas y última solución. */
    }
    
    
    
}
