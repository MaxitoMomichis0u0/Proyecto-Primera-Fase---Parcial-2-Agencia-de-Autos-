
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
    
    public void insertarEmpleado(empleado e){
        Connection cn= BD.conectarBD();
        
        try {
            PreparedStatement si= cn.prepareStatement("insert into empleados (nombre,correo,contrasena,rol) values (?,?,?,?)");
            si.setString(1, e.getNombre());
            si.setString(2, e.getCorreo());
            si.setString(3, e.getContrasena());
            si.setString(4, e.getRol());
            si.executeUpdate();
            
        } catch (Exception er) {
            JOptionPane.showMessageDialog(null, "Error al hacer la operacion: " +er);
        }
        BD.cerrarBD();
    }
    
    public empleado obtenerEmpleado(String correo, String contrasena){
        Connection cn= BD.conectarBD();
        try {
            PreparedStatement buscar= cn.prepareStatement("select * from empleados where correo=? and contrasena=?");
            buscar.setString(1, correo);
            buscar.setString(2, contrasena);
            ResultSet rs= buscar.executeQuery();
            if(rs.next()){
                return new empleado(rs.getInt("id"), rs.getString("nombre"), 
                rs.getString("correo"), rs.getString("contrasena"), rs.getString("rol"));
            }
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
      return null;
}
    
    /** @return true si se eliminó; false si no (ya se mostró el motivo). */
    public boolean eliminarEmpleado(int id) {

        Connection cn = BD.conectarBD();
        boolean eliminado = false;

        try {
            PreparedStatement eliminar = cn.prepareStatement("DELETE FROM empleados WHERE id = ?");
            eliminar.setInt(1, id);
            eliminado = eliminar.executeUpdate() > 0;
            if (!eliminado) {
                JOptionPane.showMessageDialog(null, "No se encontró el empleado a eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLIntegrityConstraintViolationException error) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar el empleado porque tiene servicios asociados.", "No se pudo eliminar", JOptionPane.WARNING_MESSAGE);
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " +error);
        }

        BD.cerrarBD();
        return eliminado;

    }
}