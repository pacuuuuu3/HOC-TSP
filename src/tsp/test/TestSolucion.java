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
    private Solucion s, s2; 

    /**
     * Constructor.
     * Incializa instancia de TSP.
     */
    public TestSolucion(){
	TSP.inicializa(0);
	this.sol = new int[3];
	for(int i = 0; i < 3; ++i)
	    sol[i] = i+1;
	int[] sol2 = {268, 270, 277};
	this.s = new Solucion(this.sol);
	this.s2 = new Solucion(sol2);
    }
    
    /**
     * Prueba unitaria para {@link Solucion#vecino}.
     */
    @Test public void testVecino(){
	
	Assert.assertTrue(s.getValor() - (1778054.73 + TSP.DEFAULT_DISTANCE) < 0.005);
	s = s.vecino();
	Assert.assertTrue(s.getValor() - (1778054.73 + TSP.DEFAULT_DISTANCE) < 0.005);
    }

    /**
     * Prueba unitaria para {@link Solucion#costo}.
     */
    @Test public void testCosto(){
	Assert.assertTrue(s.costo() > 0);
	System.out.println(s.costo());
	Assert.assertTrue(s.costo() <= 1);
    }

    /**
     * Prueba unitaria para {@link Solucion#factible}.
     */
    @Test public void testFactible(){
	Assert.assertFalse(s.factible());
	Assert.assertTrue(s2.factible());
    }

    /**
     * Prueba unitaria para {@link Solucion#maxP}.
     */
    @Test public void testMaxP(){
	Assert.assertTrue(s.maxP()==1778054.73);
	Assert.assertTrue(s2.maxP() == 2723789.75);
    }

    /**
     * Prueba unitaria para {@link Solucion#avgP}.
     */
    @Test public void testAvgP(){
	Assert.assertTrue(s.avgP()==1778054.73);
	Assert.assertTrue(s2.avgP()== ((774005.28+2723789.75+1988686.23)/3));
    }
}
