package tsp.test;

import tsp.TSP;
import tsp.Solucion;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link Solucion}.
 * @author Victor Zamora Gutierrez
 * @version 1.0
 */
public class TestSolucion{

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    private int[] sol;
    private Solucion s; 

    /**
     * Constructor.
     * Incializa instancia de TSP.
     */
    public TestSolucion(){
	TSP.inicializa();
	this.sol = new int[3];
	for(int i = 0; i < 3; ++i)
	    sol[i] = i+1;
	this.s = new Solucion(this.sol, 0);
    }
    
    /**
     * Prueba unitaria para {@link Solucion#vecino}.
     */
    @Test public void testVecino(){
	
	Assert.assertTrue(s.getValor() - (1778054.73 + TSP.DEFAULT_DISTANCE) < 0.005);
	s.vecino();
	Assert.assertTrue(s.getValor() - (1778054.73 + TSP.DEFAULT_DISTANCE) < 0.005);
    }

    /**
     * Prueba unitaria para {@link Solucion#costo}.
     */
    @Test public void testCosto(){
	Assert.assertTrue(s.costo() > 0 && s.costo() <= 1);
    }
}
