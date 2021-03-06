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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class TSP{

    public static Ciudad[] ciudades; /* Arreglo de ciudades */
    public static double[][] distancias; /* Arreglo de distancias entre las ciudades */
    private static Conexion c; /* Conexión a la base de datos */
    public static final double DEFAULT_DISTANCE = setDefaultDistance(); /* Distancia por omisión de dos ciudades desconectadas */
    public static final int L = 500; /* Tamaño del lote */
    public static final double EPSILON = 0.0001; /* Epsilon de la temperatura */
    public static final double EPSILONP = 0.0001; /* Valor del equilibrio térmico */
    public static final double PHI = 0.9; /* Factor de enfriamiento */
    public static Random random; /* Generador de números aleatorios */
    public static long semilla; /* La semilla de esta instancia de TSP */
    
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
	semilla = seed; /* Guardo la semilla para usarla en otras cosas */
	random = new Random(seed);
	llenaCiudades();
	llenaDistancias();
	c.cierraConexion();
    }

    /**
     * Algoritmo para calcular un lote
     * @param temperatura - La temperatura del sistema
     * @param s - La solución para la cuál calculamos el lote
     * @return un par con el costo promedio del lote y la última solución aceptada
     */
    public static Par<Double, Solucion> calculaLote(double temperatura, Solucion s){
	int c = 0; /* Número de soluciones aceptadas hasta el momento */
	double r = 0; /* La suma de los costos de las soluciones */
	Solucion s1 = null; /* La siguiente solución por calcular */
	int intentos = L*L; /* Número de intentos máximo */
	while(c < L && (intentos-- != 0)){
	    s1 = s.vecino();
	    if(s1.getCosto() <= (s.getCosto() + temperatura)){
		s = s1;
		c++;
		r += s1.getCosto();
	    }
	}
	if(intentos == 0)
	    return null;
	return new Par<Double, Solucion>(new Double(r/L), s); /* Promedio de las soluciones aceptadas y última solución. */
    }
    
    /**
     * Método de aceptación por umbrales en el recocido simulado 
     * @param temperatura - Temperatura inicial.
     * @param s - La solución inicial
     * @return La mejor solución obtenida.
     */
    public static Solucion aceptacionPorUmbrales(double temperatura, Solucion s){
	Solucion minima = s; /* La solución que vamos a regresar */
	double p = Double.MAX_VALUE; /* p = infinito */
	double p1; /* p' en el pdf */
	while(temperatura > EPSILON){
	    p1 = 0;
	    while(Math.abs(p-p1) > EPSILONP){
		p1 = p;
		Par<Double, Solucion> par = calculaLote(temperatura, s); /* Calculamos un nuevo lote */
		if(par == null)
		    return minima;
		p = par.primero;
		s = par.segundo;
		if(s.getCosto() < minima.getCosto()){
		    minima = s;
		}
	    }
	    temperatura *= PHI; /* Multiplicamos por el factor de enfriamiento */
	}
	return minima;
    }

    /**
     * Método de aceptación por umbrales que además guarda los valores de las soluciones aceptadas 
     * @param temperatura - Temperatura inicial.
     * @param s - La solución inicial
     * @return La mejor solución obtenida.
     */
    public static Solucion aceptacionPorUmbralesGuarda(double temperatura, Solucion s){
	Solucion minima = s; /* La solución que vamos a regresar */
	try{
	    File file = new File("graficas/Corrida" + semilla + ".txt"); /* Archivo sobre el que escribimos */
	    file.createNewFile();
	    FileWriter writer = new FileWriter(file, true); /* Escritor */
	    
	    double p = Double.MAX_VALUE; /* p = infinito */
	    double p1; /* p' en el pdf */
	    while(temperatura > EPSILON){
		p1 = 0;
		while(Math.abs(p-p1) > EPSILONP){
		    p1 = p;
		    Par<Double, Solucion> par = calculaLote(temperatura, s); /* Calculamos un nuevo lote */
		    p = par.primero;
		    s = par.segundo;
		    writer.write("E: "+ s.getCosto() + "\n"); /* Guardamos las aceptadas */
		    if(s.getCosto() < minima.getCosto()){
			minima = s;
		    }
		}
		temperatura *= PHI; /* Multiplicamos por el factor de enfriamiento */
	    }
	    writer.flush();
	    writer.close();
	}catch(IOException e){
	    System.out.println(e.getMessage());
	}
	return minima;
    }

    
}
