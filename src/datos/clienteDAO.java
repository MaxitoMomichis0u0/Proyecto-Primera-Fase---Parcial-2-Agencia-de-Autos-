
package datos;

import java.util.*;
import javax.swing.*;
import java.sql.*;
import patrones.conexion;
import modelos.cliente;

public class clienteDAO {
    
    conexion BD = conexion.getInstance(); 
    
    public List <cliente> listarClientes() {
        
        Connection cn= BD.conectarBD();
        List<cliente> lista = new ArrayList<>();
        
        try {
            PreparedStatement seleccionarTodo = cn.prepareStatement("SELECT id, nombre, telefono FROM clientes");
            
            ResultSet busq = seleccionarTodo.executeQuery();
            
            while (busq.next()) {
               
                int id = busq.getInt("id");
                String nombre = busq.getString("nombre");
                String telefono = busq.getString("telefono");
                
                cliente c = new cliente(id, nombre, telefono);
                
                lista.add(c);
                
                }    
                
            } catch (Exception error) {
                
            JOptionPane.showMessageDialog(null, "Error al intentar crear la lista:" +error);
            
        }
        
        BD.cerrarBD();
        return lista;
        
    }
        
        
    public void insertarCliente (cliente c) {
        
        Connection cn = BD.conectarBD();
        
        try {
            
            PreparedStatement insertar = cn.prepareStatement("INSERT INTO clientes (nombre, telefono) VALUES (?,?)");
            
            insertar.setString(1, c.getNombre());
            insertar.setString(2, c.getTelefono());
            
            insertar.executeUpdate();
            
            // JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.");
            
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error:" +error);

        } 
        
        BD.cerrarBD();
        
    }
    
    public void modificarCliente(cliente c){
        Connection cn= BD.conectarBD();
        
        try {
            PreparedStatement actu= cn.prepareStatement("update clientes set nombre=?, telefono=? where id=?");
            actu.setString(1, c.getNombre());
            actu.setString(2, c.getTelefono());
            actu.setInt(3, c.getId());
            actu.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:" +e);
        }
        BD.cerrarBD();
    }
    
    /** @return true si se eliminó; false si no (ya se mostró el motivo). */
    public boolean eliminarCliente(int id){
        Connection cn= BD.conectarBD();
        boolean eliminado = false;

        try {
            PreparedStatement eliminar= cn.prepareStatement("delete from clientes where id=?");
            eliminar.setInt(1, id);
            eliminado = eliminar.executeUpdate() > 0;
            if (!eliminado) {
                JOptionPane.showMessageDialog(null, "No se encontró el cliente a eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar el cliente porque tiene servicios asociados.", "No se pudo eliminar", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:" +e);
        }
        BD.cerrarBD();
        return eliminado;
    }
    
    public void iniciarSesion(){
        Connection cn= BD.conectarBD();
        
        try {
            PreparedStatement eliminar= cn.prepareStatement("");
        } catch (Exception e) {
        }
        
        
        
    }
}
