package tsp;

/**
 * Clase principal en el recocido simulado.
 * @author Víctor Zamora Gutiérrez
 * @version 1.0
 */
public class Recocido{

    public static void main(String[] args){
	TSP.inicializa(0);
	int[] ciudades = {271, 1, 269, 277, 272, 218};
	Solucion s = TSP.aceptacionPorUmbrales(4.0, new Solucion(ciudades));
	System.out.printf("Valor final: %f, Costo: %f, Factible: %b\n", s.getValor(), s.costo(), s.factible());
	for(int fi : s.getSolucion())
	    System.out.printf("%d, ", fi);
    }
    
}
