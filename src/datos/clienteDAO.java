
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
        
        
    public void insertarAuto(cliente c) {
        
        Connection cn = BD.conectarBD();
        
        try {
            
            PreparedStatement insertar = cn.prepareStatement("INSERT INTO clientes (nombre, telefono) VALUES (?,?)");
            
            insertar.setString(1, c.getNombre());
            insertar.setString(2, c.getTelefono());
            
            insertar.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.");
            
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error:" +error);

        } 
        
        BD.cerrarBD();
        
    }
    
}
