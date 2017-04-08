package tsp;

import java.util.Random;

/**
 * Clase que representa una solución del TSP 
 * @author Victor Zamora Gutierrez
 * @version 1.0
 */
public class Solucion{
    
    private int[] solucion; /* Arreglo de ids */
    private double costo; /* Valor de la solución */
    public static final double C = 2; /* Valor para la función de costo */
    private static double maximo; /* La distancia máxima entre las Ciudades de la solución */
    private static double promedio; /* El promedio de las distancias */
    
    /**
     * Constructor 
     * @param solucion - Arreglo de ids que representa a la solución
     */
    public Solucion(int[] solucion){
	this.solucion = solucion;
	maximo = this.maxP();
	promedio = this.avgP();
	this.costo = this.costo();
    }

    /**
     * Construye una solucón con un arreglo y el valor de esta (para no tener que recalcular siempre).
     * @param solucion - Arreglo de ids que representa a la solución
     * @param valor - El valor de la solución.
     */
    public Solucion(int[] solucion, double valor){
	this.solucion = solucion;
	this.costo = valor;
    }

    /**
     * Función vecino para el recocido simulado 
     * Transforma a la solución en otra solución vecino y cambia su valor
     */
    public Solucion vecino(){
	int cambio1, cambio2; /* Índices a intercambiar */
	cambio1 = cambio2 = 0;
	while(cambio1 == cambio2){ /* Para asegurarnos de que haya un intercambio */
	    cambio1 = TSP.random.nextInt(solucion.length);
	    cambio2 = TSP.random.nextInt(solucion.length);
	}
	int[] nuevaSolucion = new int[solucion.length]; /* Arreglo de enteros para la nueva Solución */
	System.arraycopy(solucion, 0, nuevaSolucion, 0, solucion.length);
	double nuevoValor = this.costo; /* Valor de la nueva solución */
	
	if(cambio1-1 >= 0)
	    nuevoValor -= dPrima(nuevaSolucion[cambio1-1], nuevaSolucion[cambio1])/(promedio*(solucion.length-1));
	if(cambio2-1 >= 0)
	    nuevoValor -= dPrima(nuevaSolucion[cambio2-1], nuevaSolucion[cambio2])/(promedio*(solucion.length-1));
	if(cambio1+1 < nuevaSolucion.length)
	    nuevoValor -= dPrima(nuevaSolucion[cambio1], nuevaSolucion[cambio1+1])/(promedio*(solucion.length-1));
	if(cambio2+1 < nuevaSolucion.length)
	    nuevoValor -= dPrima(nuevaSolucion[cambio2], nuevaSolucion[cambio2+1])/(promedio*(solucion.length-1));
	int temp = nuevaSolucion[cambio1]; /* Variable temporal */
	nuevaSolucion[cambio1] = nuevaSolucion[cambio2];
	nuevaSolucion[cambio2] = temp;
	if(cambio1-1 >= 0)
	    nuevoValor += dPrima(nuevaSolucion[cambio1-1], nuevaSolucion[cambio1])/(promedio*(solucion.length-1));
	if(cambio2-1 >= 0)
	    nuevoValor += dPrima(nuevaSolucion[cambio2-1], nuevaSolucion[cambio2])/(promedio*(solucion.length-1));
	if(cambio1+1 < nuevaSolucion.length)
	    nuevoValor += dPrima(nuevaSolucion[cambio1], nuevaSolucion[cambio1+1])/(promedio*(solucion.length-1));
	if(cambio2+1 < nuevaSolucion.length)
	    nuevoValor += dPrima(nuevaSolucion[cambio2], nuevaSolucion[cambio2+1])/(promedio*(solucion.length-1));
	return new Solucion(nuevaSolucion, nuevoValor);
    }

    /**
     * Regresa el costo de la solucion 
     * @return El costo de la solución
     */
    public double getCosto(){
	return this.costo;
    }

    /** 
     * Regresa el arreglo que representa a la solución
     * @return Arreglo con índices de la solución
     */
    public int[] getSolucion(){
	return this.solucion;
    }

    /**
     * Evalúa la solución en la función de costo
     * @return El costo de la solución
     */
    public double costo(){
	double suma = 0.0; /* Suma de las d's */
	for(int i = 1; i < solucion.length; ++i)
	    suma += dPrima(solucion[i-1], solucion[i]);
	return (suma / (promedio*(solucion.length-1)));
    }

    /** 
     * Nos dice si la solución es factible.
     * @return si la solución es factible. 
     */
    public boolean factible(){
	for(int i = 0; i < solucion.length-1; ++i)
	    if(TSP.getDistancia(solucion[i], solucion[i+1]) == TSP.DEFAULT_DISTANCE)
		return false;
	return true;
    }

    /**
     * Saca la distancia máxima entre los elementos de la solución.
     * @return La distancia máxima entre los elementos de la solución
     */
    public double maxP(){
	double max = 0; /* El máximo que hemos visto */
	for(int i = 0; i < solucion.length; i++)
	    for(int j = i; j < solucion.length; j++)
		if(TSP.distancias[solucion[i]][solucion[j]] > max)
		    max = TSP.distancias[solucion[i]][solucion[j]];
	return max;	
    }

    /**
     * Sacamos el promedio de las distancias existentes en la gráfica.
     * @return El promedio de las distancias 
     */
    public double avgP(){
	int distancias = 0; /* El número de distancias sumadas */
	double suma = 0; /* La suma de las distancias */
	for(int i = 0; i < solucion.length; i++)
	    for(int j = i; j < solucion.length; j++)
		if(TSP.distancias[solucion[i]][solucion[j]] > 0){
		    distancias++;
		    suma += TSP.distancias[solucion[i]][solucion[j]];
		}
	return suma/distancias;
    }

    /**
     * Sacamos la d' para calcular la función de costo
     * @param i - Id de la primera ciudad
     * @param j - Id de la segunda ciudad
     * @return la función d' que regresa la distancia de las ciudades si están conectadas y MaxP * C e.o.c.
     */
    public static double dPrima(int i, int j){
	if(TSP.distancias[i][j] > 0)
	    return TSP.distancias[i][j];
	else
	    return maximo*C;
    }

    /**
     * Regresa el valor (la suma de las distancias)
     * @return la suma de las distancias de la solución
     */
    public double getValor(){
	double regreso = 0.0; /* Valor a regresar */
	for(int i = 0; i < this.solucion.length -1; ++i)
	    regreso += TSP.getDistancia(solucion[i], solucion[i+1]);
	return regreso;
    }
    
    
    /**
     * Regresa una cadena con información de la Solución.
     * @return Una cadena con información de la Solución.
     */
    @Override
    public String toString(){
	String regreso = ""; /* Cadena a regresar */
	regreso += "Valor: " + getValor() + ", Costo: " + this.getCosto() + ", factible: " + this.factible() + "\n";
	regreso += "Ciudades: \n";
	for(int ciudad : this.solucion)
	    regreso += ciudad + ", ";
	regreso += "\n";
	return regreso;	
    }
    
}
