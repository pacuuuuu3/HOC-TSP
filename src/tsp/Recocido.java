package tsp;

import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * Clase principal en el recocido simulado.
 * @author Víctor Zamora Gutiérrez
 * @version 1.0
 */
public class Recocido extends Thread{

    private static int[] ciudades; /* Arreglo de ciudades sobre el que se realiza el recocido */
    
    /**
     * Escribe la solución en un archivo
     * @param s - La solución a escribir.
     * @param seed - La semilla con la que se obtuvo la solución (para nombrar el archivo).     
     */
    public static void escribeArchivo(Solucion s, long seed){
	try{
	    File file = new File("pruebas/Corrida" + seed + ".txt"); /* Archivo sobre el que escribimos */
	    file.createNewFile();
	    FileWriter writer = new FileWriter(file); /* Escritor */
	    writer.write(s.toString());
	    writer.flush();
	    writer.close();
	}catch(IOException e){
	    System.err.println(e.getMessage());
	}
    }

    /** 
     * Lee el conjunto de ciudades al cuál se le aplica el recocido desde un archivo.
     * El archivo debe tener un conjunto de enteros separados por comas, en la misma línea
     * Por ejemplo:
     * 1, 2, 3, 4, 5
     * @param archivo - Nombre del archivo con el conjunto de ciudades. 
     */
    public static void leeCiudades(String archivo){
	BufferedReader br = null;
	try{
	    File file = new File(archivo); /* Archivo a leer */
	    FileReader fr = new FileReader(file);
	    br = new BufferedReader(fr);
	    String s = br.readLine(); /* Esperamos que todas las ciudades estén en la misma línea */
	    String[] enteros = s.split(", ");
	    ciudades = new int[enteros.length];
	    int cont = 0; /* contador */
	    for(String ent: enteros)
		ciudades[cont++] = Integer.parseInt(ent);
	}catch(FileNotFoundException e){
	    System.err.println("El archivo no existe");
	}catch(IOException e){
	    System.err.println(e.getMessage());
	}finally{
	    try{if(br != null)br.close();}catch(Exception e){}
	}
    }

    /**
     * Ejecuta un recocido con una semilla dada y un conjunto de ciudades sacado de un archivo.
     * @param args - Lista de argumentos (dados por el usuario)
     */
    public static void main(String[] args){
	try{
	    long seed = Long.parseLong(args[0]); /* Semilla */
	    leeCiudades(args[1]);
	    TSP.inicializa(seed);
	    Solucion cruda = new Solucion(ciudades);
	    Solucion sol = TSP.aceptacionPorUmbralesGuarda(4.0, cruda);
	    System.out.println(sol); /* Imprimimos la solución que obtuvimos */	    
	}catch(Exception e){
	    System.err.println("Uso del programa java -jar tsp.jar <semilla> <archivo_ciudades>, donde semilla es la semilla a usar y archivo_ciudades es un archivo con los ids de las ciudades de la instancia a evaluar, separados por coma y espacio."); 
	}
     }   
}
