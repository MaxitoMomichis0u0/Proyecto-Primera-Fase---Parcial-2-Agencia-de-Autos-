
package patrones;

import java.sql.*;
import javax.swing.*;

public class conexion {
    
    private static Connection conexion;
    private static conexion instancia;
    
    private static final String url = "jdbc:mysql://localhost/agencia_de_autos";
    private static final String user = "root";
    private static final String pass = "admin";

    private conexion() {
        
    }
    
    public Connection conectarBD() {
        
        try {
            
            if (conexion == null || conexion.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexión exitosa");
            
         }
            return conexion;
            
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null,"Error al conectar: " +error);
        }
        
        return conexion;
    }
    
    public void cerrarBD () {
         
        try {
            conexion.close();
            System.out.println("Conexion cerrada");
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error al cerrar");
        } 
        
    }
    
    public static conexion getInstance() {
        
        if (instancia == null) {
            instancia= new conexion();
        }
        
        return instancia;
    }
    
}
