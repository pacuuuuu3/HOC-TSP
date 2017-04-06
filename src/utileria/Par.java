package utileria;

/**
 * Clase que representa a un par de genéricos en Java.
 * @author Víctor Zamora Gutiérrez
 * @version 1.0 
 */
public class Par<X, Y>{
    private final X primero; /* Primer elemento del par */
    private final Y segundo; /* Segundo elemento del par */

    /**
     * Constructor.
     * @param x - El primer elemento del par.
     * @param y - El segundo elemento del par.
     */
    public Par(X x, Y y){
	this.primero = x;
	this.segundo = y;
    }

    /**
     * Regresa el primer elemento del par.
     * @return el primer elemento del par.
     */
    public X getPrimero(){
	return this.primero;
    }

    /**
     * Regresa el segundo elemento del par.
     * @return el segundo elemento del par.
     */
    public Y getSegundo(){
	return this.segundo;
    }
}
