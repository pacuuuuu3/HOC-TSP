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
    
    public static void main(String[] args){
	leeCiudades("Ciudades.txt");
	for(int ciudad: ciudades)
	    System.out.println(ciudad);
	// int[] ciudadesCanek = {1, 5, 9, 12, 16, 22, 23, 29, 30, 31,
    // 			       39, 48, 52, 56, 58, 62, 65, 66, 70, 75,
    // 			       80, 84, 86, 90, 92, 94, 95, 101, 107,
    // 			       117, 119, 122, 133, 135, 143, 144, 146,
    // 			       147, 150, 158, 159, 160, 166, 167, 176,
    // 			       178, 179, 185, 186, 188, 190, 191, 194,
    // 			       198, 200, 203, 207, 209, 213, 215, 216,
    // 			       220, 221, 224, 227, 232, 233, 235, 238,
    // 			       241, 244, 248, 250, 254, 264, 266, 274, 276};
    // 	long startingSeed = Long.parseLong(args[0]);
    // 	long endingSeed = Long.parseLong(args[1]);
    // 	for(long i = startingSeed; i < endingSeed; ++i){
    // 	   TSP.inicializa(i);
    // 	   Solucion cruda = new Solucion(ciudadesCanek); /* Solución con las ciudades de Canek */
    // 	   Solucion s = TSP.aceptacionPorUmbrales(4.0, cruda); /* Solución tras el recocido */
    // 	  escribeArchivo(s, i);
    // 	}
    // 	TSP.inicializa(72);
    // 	Solucion cruda = new Solucion(ciudadesCanek);
    // 	Solucion sol = TSP.aceptacionPorUmbralesGuarda(4.0, cruda);
    // 	System.out.println(sol);

    // 	int factibles = 0;
    // 	double minCosto = Double.MAX_VALUE;
    // 	int mejorsemilla = 0;
    // 	try{
    // 	    for(int i = 0; i < 150; ++i){
    // 		File file = new File("pruebas/Corrida" + i + ".txt");
    // 		FileReader fr = new FileReader(file);
    // 		BufferedReader br = new BufferedReader(fr);
    // 		String s;
    // 		s = br.readLine();
    // 		String parts[] = s.split(",");
    // 		Scanner sc = new Scanner(parts[1]);
    // 		String parts2[] = parts[1].split(" "); 
    // 		double costo = Double.parseDouble(parts2[2]);
    // 		if(costo < minCosto){
    // 		    minCosto = costo;
    // 		    mejorsemilla = i;
    // 		}
    // 		if(s.contains("factible: true"))
    // 		    factibles++;
    // 	    }
    // 	}catch(IOException e){
    // 	}
    // 	System.out.println((double)factibles/150.0);
    // 	System.out.println(minCosto);
    // 	System.out.println(mejorsemilla);
     }   
}
