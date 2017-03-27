/**
 * Programa que utiliza recocido simulado (Simulated Annealing) para resolver una versión modificada del TSP
 * @author Victor Zamora Gutierrez
 * @version 1.0
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TSP{

    private Ciudad[] ciudades; /* Arreglo de ciudades */

    /**
     * Llena el arreglo de ciudades utilizando la base de datos 
     */
    public void llenaCiudades(){
	Class.forName("org.sqlite.JDBC"); /* Carga el driver de sqlite */
	Connection connection = null; /* Conexión a la base de datos */
	connection = DriverManager.getConnection("jdbc:sqlite:sample.db"); /* Inicializamos la conexión */
    }

}
