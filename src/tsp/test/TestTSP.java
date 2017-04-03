package tsp.test;

import tsp.TSP;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link TSP}.
 * @author Victor Zamora Gutierrez
 * @version 1.0
 */
public class TestTSP {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /**
     * Prueba unitaria para {@link TSP#getTamano}.
     */
    @Test public void testGetTamano(){
	Assert.assertTrue(TSP.getTamano() == 277);
    }

    /**
     * Prueba unitaria para {@link TSP#llenaCiudades}.
     */
    @Test public void testLlenaCiudades(){
	TSP.llenaCiudades();
	Assert.assertTrue(TSP.ciudades.length == 278);
	Assert.assertTrue(TSP.ciudades[277].toString().equals("Odesa, Ukraine"));
    }

    /**
     * Prueba unitaria para {@link TSP#llenaDistancias}.
     */
    @Test public void testLlenaDistancias(){
	TSP.llenaDistancias();
	for(int i = 0; i < 278; ++i)
	    Assert.assertTrue(TSP.distancias[i][i] == 0.0);
	Assert.assertTrue(TSP.distancias[11][229] == 1671159.82);
    }
    
    
    

    
}
