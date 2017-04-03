package tsp;

/**
 * Clase que representa la conexión a la base de datos 
 * @author Victor Zamora Gutierrez
 * @version 1.0 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion{

    private Connection c = null; /* Conexión a la base de datos */

    /**
     * Método que crea una conexión a la base de datos 
     */
    public Conexion(){
	/* Intentamos crear la conexión... */
	try{
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:db/tsp.db"); /* Inicializamos la conexión */
	    /* Conexión creada */
	}catch ( Exception e ) {
		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		System.exit(0);
	}
    }

    /**
     * Realiza una consulta
     * @param consulta - Una consulta sql
     * @return Un ResultSet con el resultado de la consulta
     */
    public ResultSet consulta(String consulta){
	ResultSet rs = null;
	try{
	    Statement stmt = c.createStatement();
	    rs = stmt.executeQuery(consulta);
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}
	return rs;
    }
}
