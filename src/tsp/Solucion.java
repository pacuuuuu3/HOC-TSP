package tsp;

import java.util.Random;

/**
 * Clase que representa una solución del TSP 
 * @author Victor Zamora Gutierrez
 * @version 1.0
 */
public class Solucion{
    
    private int[] solucion; /* Arreglo de ids */
    private double valor; /* Valor de la solución */
    
    /**
     * Constructor 
     * @param solucion - Arreglo de ids que representa a la solución
     */
    public Solucion(int[] solucion){
	this.solucion = solucion;
	this.valor = 0.0;
	for(int i = 0; i < solucion.length-1; ++i){
	    this.valor += TSP.getDistancia(solucion[i], solucion[i+1]);
	}
    }

    /**
     * Construye una solucón con un arreglo y el valor de esta (para no tener que recalcular siempre).
     * @param solucion - Arreglo de ids que representa a la solución
     * @param valor - El valor de la solución.
     */
    public Solucion(int[] solucion, double valor){
	this.solucion = solucion;
	this.valor = valor;
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
	double nuevoValor = this.valor; /* Valor de la nueva solución */
	
	if(cambio1-1 >= 0)
	    nuevoValor -= TSP.getDistancia(nuevaSolucion[cambio1-1], nuevaSolucion[cambio1]);
	if(cambio2-1 >= 0)
	    nuevoValor -= TSP.getDistancia(nuevaSolucion[cambio2-1], nuevaSolucion[cambio2]);
	if(cambio1+1 < nuevaSolucion.length)
	    nuevoValor -= TSP.getDistancia(nuevaSolucion[cambio1], nuevaSolucion[cambio1+1]);
	if(cambio2+1 < nuevaSolucion.length)
	    nuevoValor -= TSP.getDistancia(nuevaSolucion[cambio2], nuevaSolucion[cambio2+1]);
	int temp = nuevaSolucion[cambio1]; /* Variable temporal */
	nuevaSolucion[cambio1] = nuevaSolucion[cambio2];
	nuevaSolucion[cambio2] = temp;
	if(cambio1-1 >= 0)
	    nuevoValor += TSP.getDistancia(nuevaSolucion[cambio1-1], nuevaSolucion[cambio1]);
	if(cambio2-1 >= 0)
	    nuevoValor += TSP.getDistancia(nuevaSolucion[cambio2-1], nuevaSolucion[cambio2]);
	if(cambio1+1 < nuevaSolucion.length)
	    nuevoValor += TSP.getDistancia(nuevaSolucion[cambio1], nuevaSolucion[cambio1+1]);
	if(cambio2+1 < nuevaSolucion.length)
	    nuevoValor += TSP.getDistancia(nuevaSolucion[cambio2], nuevaSolucion[cambio2+1]);
	return new Solucion(nuevaSolucion, nuevoValor);
    }

    /**
     * Regresa el valor de la solucion (no es el costo, pues no esta normalizado) 
     * @return El valor de la solución
     */
    public double getValor(){
	return this.valor;
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
	return this.valor/(this.solucion.length * TSP.DEFAULT_DISTANCE);
    }
}
