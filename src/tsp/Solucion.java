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
    private Random random; /* Instancia para generar números aleatorios */
    
    /**
     * Constructor 
     * @param solucion - Arreglo de ids que representa a la solución
     * @param seed - Semilla para el generador de números aleatorios
     */
    public Solucion(int[] solucion, long seed){
	this.solucion = solucion;
	this.valor = 0.0;
	for(int i = 0; i < solucion.length-1; ++i){
	    this.valor += TSP.getDistancia(solucion[i], solucion[i+1]);
	}
	this.random = new Random(seed);
    }

    /**
     * Función vecino para el recocido simulado 
     * Transforma a la solución en otra solución vecino y cambia su valor
     */
    public void vecino(){
	int cambio1, cambio2; /* Índices a intercambiar */
	cambio1 = cambio2 = 0;
	while(cambio1 == cambio2){ /* Para asegurarnos de que haya un intercambio */
	    cambio1 = this.random.nextInt(solucion.length);
	    cambio2 = this.random.nextInt(solucion.length);
	}
	if(cambio1-1 >= 0)
	    this.valor -= TSP.getDistancia(solucion[cambio1-1], solucion[cambio1]);
	if(cambio2-1 >= 0)
	    this.valor -= TSP.getDistancia(solucion[cambio2-1], solucion[cambio2]);
	if(cambio1+1 < solucion.length)
	    this.valor -= TSP.getDistancia(solucion[cambio1], solucion[cambio1+1]);
	if(cambio2+1 < solucion.length)
	    this.valor -= TSP.getDistancia(solucion[cambio2], solucion[cambio2+1]);
	int temp = solucion[cambio1]; /* Variable temporal */
	solucion[cambio1] = solucion[cambio2];
	solucion[cambio2] = temp;
	if(cambio1-1 >= 0)
	    this.valor += TSP.getDistancia(solucion[cambio1-1], solucion[cambio1]);
	if(cambio2-1 >= 0)
	    this.valor += TSP.getDistancia(solucion[cambio2-1], solucion[cambio2]);
	if(cambio1+1 < solucion.length)
	    this.valor += TSP.getDistancia(solucion[cambio1], solucion[cambio1+1]);
	if(cambio2+1 < solucion.length)
	    this.valor += TSP.getDistancia(solucion[cambio2], solucion[cambio2+1]);
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
