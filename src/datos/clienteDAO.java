
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
    
    public void eliminarCliente(int id){
        Connection cn= BD.conectarBD();
        
        try {
            PreparedStatement eliminar= cn.prepareStatement("delete from clientes where id=?");
            eliminar.setInt(1, id);
            eliminar.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:" +e);
        }
        BD.cerrarBD();
    }
    
    public void iniciarSesion(){
        Connection cn= BD.conectarBD();
        
        try {
            PreparedStatement eliminar= cn.prepareStatement("");
        } catch (Exception e) {
        }
        
        
        
    }
}
