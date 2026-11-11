
package datos;

import java.util.*;
import javax.swing.*;
import java.sql.*;
import patrones.conexion;
import modelos.empleado;

public class empleadoDAO {
    
    conexion BD = conexion.getInstance(); 
    
    public List <empleado> listarEmpleados() {
        
        Connection cn= BD.conectarBD();
        List<empleado> lista = new ArrayList<>();
        
        try {
            PreparedStatement seleccionarTodo = cn.prepareStatement("SELECT id, nombre, correo, contrasena, rol FROM empleados");
            
            ResultSet busq = seleccionarTodo.executeQuery();
            
            while (busq.next()) {
               
                int id = busq.getInt("id");
                String nombre = busq.getString("nombre");
                String correo = busq.getString("correo");
                String contrasena = busq.getString("contrasena");
                String rol = busq.getString("rol");
                
                empleado e = new empleado(id, nombre, correo, contrasena, rol);
                
                lista.add(e);
                
                }    
                
            } catch (Exception error) {
                
            JOptionPane.showMessageDialog(null, "Error al intentar crear la lista:" +error);
            
        }
        
        BD.cerrarBD();
        return lista;
        
    }
    
}
