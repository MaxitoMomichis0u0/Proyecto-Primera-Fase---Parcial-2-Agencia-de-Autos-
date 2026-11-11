
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
    
    public boolean validarLogin(String correo, String contrasena) {
        
        Connection cn = BD.conectarBD();
        Boolean validado = false;
        
        try {
            PreparedStatement seleccionar = cn.prepareStatement("SELECT correo, contrasena FROM empleados WHERE correo = ? AND contrasena = ?");
            
            seleccionar.setString(1, correo);
            seleccionar.setString(2, contrasena);
            
            ResultSet busq = seleccionar.executeQuery();
            
            if(busq.next()) {
               validado = true;
            } else {
               validado = false;
            }
                    
        } catch (Exception error) {
            
            JOptionPane.showMessageDialog(null, "Error al hacer la operacion: " +error);
            
        }
        return validado; 
    }
}
